package com.brokyfood.server

import com.google.gson.Gson
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.io.File
import java.net.HttpURLConnection
import java.net.URI
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

class BrokyFoodServerTest {
    private lateinit var tempDbFile: File
    private lateinit var server: BrokyFoodServer
    private val gson = Gson()

    @Before
    fun setUp() {
        tempDbFile = File.createTempFile("brokyfood_test_", ".db")
        val db = Database(tempDbFile.absolutePath)
        server = BrokyFoodServer(0, db)
        server.start()
    }

    @After
    fun tearDown() {
        server.stop()
        tempDbFile.delete()
    }

    @Test
    fun `health and 14 seeded foods are returned`() {
        val (healthCode, healthBody) = get("/api/health")
        assertEquals(200, healthCode)
        assertTrue(healthBody.contains("\"foods\":14"))

        val (code, body) = get("/yemekler/tumYemekleriGetir.php")
        assertEquals(200, code)
        val parsed = gson.fromJson(body, FoodListResponse::class.java)
        assertEquals(1, parsed.success)
        assertEquals(14, parsed.yemekler.size)
        assertEquals("Ayran", parsed.yemekler.first().name)
    }

    @Test
    fun `cart upsert, atomic quantity update, user isolation, and checkout work`() {
        // Add Baklava x2 for user_a
        val (add1Code, _) = post(
            "/yemekler/sepeteYemekEkle.php",
            mapOf(
                "yemek_adi" to "Baklava",
                "yemek_resim_adi" to "baklava.png",
                "yemek_fiyat" to "250",
                "yemek_siparis_adet" to "2",
                "kullanici_adi" to "user_a"
            )
        )
        assertEquals(200, add1Code)

        // Add Baklava x3 again for user_a -> should coalesce to quantity=5
        val (add2Code, _) = post(
            "/yemekler/sepeteYemekEkle.php",
            mapOf(
                "yemek_adi" to "Baklava",
                "yemek_resim_adi" to "baklava.png",
                "yemek_fiyat" to "250",
                "yemek_siparis_adet" to "3",
                "kullanici_adi" to "user_a"
            )
        )
        assertEquals(200, add2Code)

        // user_b should still have an empty cart
        val (_, cartBBody) = post("/yemekler/sepettekiYemekleriGetir.php", mapOf("kullanici_adi" to "user_b"))
        val cartB = gson.fromJson(cartBBody, CartListResponse::class.java)
        assertEquals(0, cartB.cartItems.size)

        // user_a should have 1 row with quantity=5
        val (_, cartABody) = post("/yemekler/sepettekiYemekleriGetir.php", mapOf("kullanici_adi" to "user_a"))
        val cartA = gson.fromJson(cartABody, CartListResponse::class.java)
        assertEquals(1, cartA.cartItems.size)
        assertEquals(5, cartA.cartItems[0].quantity)
        val cartId = cartA.cartItems[0].cartId

        // Atomic quantity update to 4
        val (updCode, _) = post(
            "/yemekler/sepetAdetGuncelle.php",
            mapOf(
                "sepet_yemek_id" to cartId.toString(),
                "kullanici_adi" to "user_a",
                "yemek_siparis_adet" to "4"
            )
        )
        assertEquals(200, updCode)

        val (_, cartA2Body) = post("/yemekler/sepettekiYemekleriGetir.php", mapOf("kullanici_adi" to "user_a"))
        val cartA2 = gson.fromJson(cartA2Body, CartListResponse::class.java)
        assertEquals(4, cartA2.cartItems[0].quantity)

        // Checkout clears user_a cart
        val (chkCode, _) = post("/yemekler/siparisTamamla.php", mapOf("kullanici_adi" to "user_a"))
        assertEquals(200, chkCode)

        val (_, cartA3Body) = post("/yemekler/sepettekiYemekleriGetir.php", mapOf("kullanici_adi" to "user_a"))
        val cartA3 = gson.fromJson(cartA3Body, CartListResponse::class.java)
        assertEquals(0, cartA3.cartItems.size)
    }

    @Test
    fun `invalid inputs and traversal are rejected`() {
        val (imgCode, _) = get("/yemekler/resimler/ayran.png")
        assertEquals(200, imgCode)

        val (badImgCode, _) = get("/yemekler/resimler/bad.txt")
        assertEquals(400, badImgCode)

        val (badUserCode, _) = post("/yemekler/sepettekiYemekleriGetir.php", mapOf("kullanici_adi" to "bad user!"))
        assertEquals(400, badUserCode)
    }

    private fun get(path: String): Pair<Int, String> {
        val conn = URI("http://127.0.0.1:${server.boundPort}$path").toURL().openConnection() as HttpURLConnection
        conn.requestMethod = "GET"
        val code = conn.responseCode
        val stream = if (code in 200..299) conn.inputStream else conn.errorStream
        val body = stream?.bufferedReader()?.readText().orEmpty()
        return code to body
    }

    private fun post(path: String, form: Map<String, String>): Pair<Int, String> {
        val conn = URI("http://127.0.0.1:${server.boundPort}$path").toURL().openConnection() as HttpURLConnection
        conn.requestMethod = "POST"
        conn.doOutput = true
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded")
        val encoded = form.entries.joinToString("&") { (k, v) ->
            "${URLEncoder.encode(k, StandardCharsets.UTF_8)}=${URLEncoder.encode(v, StandardCharsets.UTF_8)}"
        }
        conn.outputStream.use { it.write(encoded.toByteArray(StandardCharsets.UTF_8)) }
        val code = conn.responseCode
        val stream = if (code in 200..299) conn.inputStream else conn.errorStream
        val body = stream?.bufferedReader()?.readText().orEmpty()
        return code to body
    }
}
