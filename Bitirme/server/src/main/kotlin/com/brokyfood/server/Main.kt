package com.brokyfood.server

import com.google.gson.Gson
import com.sun.net.httpserver.HttpExchange
import com.sun.net.httpserver.HttpServer
import java.io.File
import java.net.InetSocketAddress
import java.net.URLDecoder
import java.nio.charset.StandardCharsets
import java.util.concurrent.Executors

class BrokyFoodServer(
    port: Int,
    private val db: Database,
    private val apkFile: File? = null
) {
    private val gson = Gson()
    private val usernameRegex = Regex("^[a-zA-Z0-9_.-]{1,64}$")
    private val imageNameRegex = Regex("^[a-zA-Z0-9_.-]+\\.png$")
    private val httpServer: HttpServer = HttpServer.create(InetSocketAddress(port), 0)

    val boundPort: Int
        get() = httpServer.address.port

    init {
        httpServer.executor = Executors.newFixedThreadPool(8)
        httpServer.createContext("/") { ex ->
            try {
                handle(ex)
            } catch (e: Exception) {
                e.printStackTrace()
                sendJson(ex, 500, BaseApiResponse(0, "Sunucu hatasi"))
            } finally {
                ex.close()
            }
        }
    }

    fun start() {
        httpServer.start()
    }

    fun stop() {
        httpServer.stop(0)
    }

    private fun handle(ex: HttpExchange) {
        addSecurityHeaders(ex)
        val method = ex.requestMethod.uppercase()
        val rawPath = ex.requestURI.path ?: "/"
        val normalized = if (rawPath.startsWith("/yemekler/")) {
            "/" + rawPath.removePrefix("/yemekler/")
        } else {
            rawPath
        }

        when {
            (rawPath == "/" || rawPath == "/index.html") && method == "GET" -> {
                serveClasspathResource(ex, "/static/index.html", "text/html; charset=utf-8")
            }
            rawPath == "/app.js" && method == "GET" -> {
                serveClasspathResource(ex, "/static/app.js", "application/javascript; charset=utf-8")
            }
            (rawPath == "/api/health" || rawPath == "/health") && method == "GET" -> {
                val count = db.healthCheck()
                sendJson(ex, 200, mapOf("status" to "ok", "foods" to count))
            }
            rawPath == "/brokyfood.apk" && method == "GET" -> {
                if (apkFile != null && apkFile.isFile) {
                    ex.responseHeaders.set("Content-Type", "application/vnd.android.package-archive")
                    ex.responseHeaders.set("Content-Disposition", "attachment; filename=\"brokyfood.apk\"")
                    ex.sendResponseHeaders(200, apkFile.length())
                    apkFile.inputStream().use { input ->
                        ex.responseBody.use { out -> input.copyTo(out) }
                    }
                } else {
                    ex.responseHeaders.set("Location", "https://github.com/brokyewing/kotlin-egitimi/tree/main/Bitirme")
                    ex.sendResponseHeaders(302, -1)
                }
            }
            normalized.startsWith("/resimler/") && method == "GET" -> {
                val fileName = normalized.removePrefix("/resimler/")
                if (!imageNameRegex.matches(fileName) || fileName.contains("..")) {
                    sendJson(ex, 400, BaseApiResponse(0, "Gecersiz resim adi"))
                    return
                }
                serveClasspathResource(ex, "/static/resimler/$fileName", "image/png", cacheSeconds = 86400)
            }
            normalized == "/tumYemekleriGetir.php" && method == "GET" -> {
                val foods = db.getAllFoods()
                sendJson(ex, 200, FoodListResponse(yemekler = foods, success = 1))
            }
            normalized == "/sepettekiYemekleriGetir.php" && method == "POST" -> {
                val form = parseForm(ex)
                val username = form["kullanici_adi"]?.trim().orEmpty()
                if (!usernameRegex.matches(username)) {
                    sendJson(ex, 400, BaseApiResponse(0, "Gecersiz kullanici_adi"))
                    return
                }
                val items = db.getCartFoods(username)
                sendJson(ex, 200, CartListResponse(cartItems = items, success = 1))
            }
            normalized == "/sepeteYemekEkle.php" && method == "POST" -> {
                val form = parseForm(ex)
                val username = form["kullanici_adi"]?.trim().orEmpty()
                val name = form["yemek_adi"]?.trim().orEmpty()
                val image = form["yemek_resim_adi"]?.trim().orEmpty()
                val price = form["yemek_fiyat"]?.toIntOrNull() ?: -1
                val quantity = form["yemek_siparis_adet"]?.toIntOrNull() ?: -1

                if (!usernameRegex.matches(username) || name.isBlank() || name.length > 80 ||
                    !imageNameRegex.matches(image) || price <= 0 || quantity !in 1..100
                ) {
                    sendJson(ex, 400, BaseApiResponse(0, "Gecersiz sepet verisi"))
                    return
                }
                val ok = db.addToCart(name, image, price, quantity, username)
                if (ok) {
                    sendJson(ex, 200, BaseApiResponse(1, "Sepete eklendi"))
                } else {
                    sendJson(ex, 400, BaseApiResponse(0, "Sepete eklenemedi"))
                }
            }
            normalized == "/sepetAdetGuncelle.php" && method == "POST" -> {
                val form = parseForm(ex)
                val username = form["kullanici_adi"]?.trim().orEmpty()
                val cartId = form["sepet_yemek_id"]?.toIntOrNull() ?: -1
                val quantity = form["yemek_siparis_adet"]?.toIntOrNull() ?: -1

                if (!usernameRegex.matches(username) || cartId <= 0 || quantity < 0 || quantity > 100) {
                    sendJson(ex, 400, BaseApiResponse(0, "Gecersiz adet verisi"))
                    return
                }
                val ok = db.updateCartQuantity(cartId, username, quantity)
                sendJson(ex, if (ok) 200 else 404, BaseApiResponse(if (ok) 1 else 0, if (ok) "Adet guncellendi" else "Kayit bulunamadi"))
            }
            normalized == "/sepettenYemekSil.php" && method == "POST" -> {
                val form = parseForm(ex)
                val username = form["kullanici_adi"]?.trim().orEmpty()
                val cartId = form["sepet_yemek_id"]?.toIntOrNull() ?: -1

                if (!usernameRegex.matches(username) || cartId <= 0) {
                    sendJson(ex, 400, BaseApiResponse(0, "Gecersiz silme istegi"))
                    return
                }
                val ok = db.removeFromCart(cartId, username)
                sendJson(ex, if (ok) 200 else 404, BaseApiResponse(if (ok) 1 else 0, if (ok) "Sepetten silindi" else "Kayit bulunamadi"))
            }
            normalized == "/siparisTamamla.php" && method == "POST" -> {
                val form = parseForm(ex)
                val username = form["kullanici_adi"]?.trim().orEmpty()
                if (!usernameRegex.matches(username)) {
                    sendJson(ex, 400, BaseApiResponse(0, "Gecersiz kullanici_adi"))
                    return
                }
                val ok = db.checkout(username)
                sendJson(ex, if (ok) 200 else 400, BaseApiResponse(if (ok) 1 else 0, if (ok) "Siparis tamamlandi" else "Sepet bos"))
            }
            else -> {
                sendJson(ex, 404, BaseApiResponse(0, "Bulunamadi"))
            }
        }
    }

    private fun addSecurityHeaders(ex: HttpExchange) {
        ex.responseHeaders.set("X-Content-Type-Options", "nosniff")
        ex.responseHeaders.set("X-Frame-Options", "DENY")
        ex.responseHeaders.set("Referrer-Policy", "strict-origin-when-cross-origin")
    }

    private fun parseForm(ex: HttpExchange): Map<String, String> {
        val bytes = ex.requestBody.use { it.readNBytes(16 * 1024) }
        val body = String(bytes, StandardCharsets.UTF_8)
        if (body.isBlank()) return emptyMap()
        return body.split("&").mapNotNull { pair ->
            val idx = pair.indexOf('=')
            if (idx <= 0) null
            else {
                val k = URLDecoder.decode(pair.substring(0, idx), StandardCharsets.UTF_8)
                val v = URLDecoder.decode(pair.substring(idx + 1), StandardCharsets.UTF_8)
                k to v
            }
        }.toMap()
    }

    private fun serveClasspathResource(
        ex: HttpExchange,
        resourcePath: String,
        contentType: String,
        cacheSeconds: Int = 0
    ) {
        val stream = BrokyFoodServer::class.java.getResourceAsStream(resourcePath)
        if (stream == null) {
            sendJson(ex, 404, BaseApiResponse(0, "Dosya bulunamadi"))
            return
        }
        val bytes = stream.use { it.readBytes() }
        ex.responseHeaders.set("Content-Type", contentType)
        if (cacheSeconds > 0) {
            ex.responseHeaders.set("Cache-Control", "public, max-age=$cacheSeconds")
        } else {
            ex.responseHeaders.set("Cache-Control", "no-cache")
        }
        ex.sendResponseHeaders(200, bytes.size.toLong())
        ex.responseBody.use { it.write(bytes) }
    }

    private fun sendJson(ex: HttpExchange, status: Int, payload: Any) {
        val bytes = gson.toJson(payload).toByteArray(StandardCharsets.UTF_8)
        ex.responseHeaders.set("Content-Type", "application/json; charset=utf-8")
        ex.responseHeaders.set("Cache-Control", "no-store")
        ex.sendResponseHeaders(status, bytes.size.toLong())
        ex.responseBody.use { it.write(bytes) }
    }
}

fun main() {
    val port = System.getenv("PORT")?.toIntOrNull() ?: 8080
    val dbPath = System.getenv("DB_PATH") ?: "./data/brokyfood.db"
    val apkPath = System.getenv("APK_PATH") ?: "./brokyfood.apk"
    val db = Database(dbPath)
    val server = BrokyFoodServer(port, db, File(apkPath))
    Runtime.getRuntime().addShutdownHook(Thread { server.stop() })
    server.start()
    println("BrokyFood server listening on http://0.0.0.0:${server.boundPort} (db=$dbPath)")
}
