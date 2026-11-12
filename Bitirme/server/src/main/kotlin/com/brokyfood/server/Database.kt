package com.brokyfood.server

import com.google.gson.annotations.SerializedName
import java.io.File
import java.sql.Connection
import java.sql.DriverManager

data class FoodDto(
    @SerializedName("yemek_id") val id: Int,
    @SerializedName("yemek_adi") val name: String,
    @SerializedName("yemek_resim_adi") val imageUrl: String,
    @SerializedName("yemek_fiyat") val price: Int
)

data class CartFoodDto(
    @SerializedName("sepet_yemek_id") val cartId: Int,
    @SerializedName("yemek_adi") val name: String,
    @SerializedName("yemek_resim_adi") val imageUrl: String,
    @SerializedName("yemek_fiyat") val price: Int,
    @SerializedName("yemek_siparis_adet") val quantity: Int,
    @SerializedName("kullanici_adi") val username: String
)

data class FoodListResponse(
    val yemekler: List<FoodDto>,
    val success: Int = 1
)

data class CartListResponse(
    @SerializedName("sepet_yemekler") val cartItems: List<CartFoodDto>,
    val success: Int = 1
)

data class BaseApiResponse(
    val success: Int,
    val message: String
)

class Database(dbPath: String) {
    private val jdbcUrl: String
    private val lock = Any()
    private val keepAliveConnection: Connection?

    init {
        Class.forName("org.sqlite.JDBC")
        if (dbPath == ":memory:") {
            jdbcUrl = "jdbc:sqlite:file:brokyfood_mem?mode=memory&cache=shared"
            keepAliveConnection = DriverManager.getConnection(jdbcUrl)
        } else {
            val file = File(dbPath)
            file.parentFile?.mkdirs()
            jdbcUrl = "jdbc:sqlite:${file.absolutePath}"
            keepAliveConnection = null
        }
        initSchemaAndSeed()
    }

    private fun <T> withConn(block: (Connection) -> T): T = synchronized(lock) {
        DriverManager.getConnection(jdbcUrl).use { conn ->
            conn.createStatement().use { st ->
                st.execute("PRAGMA busy_timeout = 5000;")
                st.execute("PRAGMA foreign_keys = ON;")
            }
            block(conn)
        }
    }

    private fun initSchemaAndSeed() = withConn { conn ->
        conn.createStatement().use { st ->
            st.execute("PRAGMA journal_mode = WAL;")
            st.execute(
                """
                CREATE TABLE IF NOT EXISTS foods (
                    yemek_id INTEGER PRIMARY KEY,
                    yemek_adi TEXT NOT NULL UNIQUE,
                    yemek_resim_adi TEXT NOT NULL,
                    yemek_fiyat INTEGER NOT NULL
                );
                """.trimIndent()
            )
            st.execute(
                """
                CREATE TABLE IF NOT EXISTS cart_items (
                    sepet_yemek_id INTEGER PRIMARY KEY AUTOINCREMENT,
                    yemek_adi TEXT NOT NULL,
                    yemek_resim_adi TEXT NOT NULL,
                    yemek_fiyat INTEGER NOT NULL,
                    yemek_siparis_adet INTEGER NOT NULL CHECK(yemek_siparis_adet > 0 AND yemek_siparis_adet <= 100),
                    kullanici_adi TEXT NOT NULL,
                    created_at INTEGER NOT NULL DEFAULT (strftime('%s','now')),
                    UNIQUE(kullanici_adi, yemek_adi)
                );
                """.trimIndent()
            )
            st.execute(
                """
                CREATE TABLE IF NOT EXISTS orders (
                    order_id INTEGER PRIMARY KEY AUTOINCREMENT,
                    kullanici_adi TEXT NOT NULL,
                    total_items INTEGER NOT NULL,
                    total_price INTEGER NOT NULL,
                    created_at INTEGER NOT NULL DEFAULT (strftime('%s','now'))
                );
                """.trimIndent()
            )
        }

        val count = conn.createStatement().use { st ->
            st.executeQuery("SELECT COUNT(*) FROM foods").use { rs ->
                if (rs.next()) rs.getInt(1) else 0
            }
        }
        if (count == 0) {
            val seedFoods = listOf(
                FoodDto(1, "Ayran", "ayran.png", 30),
                FoodDto(2, "Baklava", "baklava.png", 250),
                FoodDto(3, "Fanta", "fanta.png", 60),
                FoodDto(4, "Izgara Somon", "izgarasomon.png", 550),
                FoodDto(5, "Izgara Tavuk", "izgaratavuk.png", 270),
                FoodDto(6, "Kadayıf", "kadayif.png", 220),
                FoodDto(7, "Kahve", "kahve.png", 160),
                FoodDto(8, "Köfte", "kofte.png", 250),
                FoodDto(9, "Lazanya", "lazanya.png", 320),
                FoodDto(10, "Makarna", "makarna.png", 280),
                FoodDto(11, "Pizza", "pizza.png", 450),
                FoodDto(12, "Su", "su.png", 20),
                FoodDto(13, "Sütlaç", "sutlac.png", 100),
                FoodDto(14, "Tiramisu", "tiramisu.png", 230)
            )
            conn.prepareStatement(
                "INSERT INTO foods (yemek_id, yemek_adi, yemek_resim_adi, yemek_fiyat) VALUES (?, ?, ?, ?)"
            ).use { ps ->
                for (f in seedFoods) {
                    ps.setInt(1, f.id)
                    ps.setString(2, f.name)
                    ps.setString(3, f.imageUrl)
                    ps.setInt(4, f.price)
                    ps.addBatch()
                }
                ps.executeBatch()
            }
        }
    }

    fun healthCheck(): Int = withConn { conn ->
        conn.createStatement().use { st ->
            st.executeQuery("SELECT COUNT(*) FROM foods").use { rs ->
                if (rs.next()) rs.getInt(1) else 0
            }
        }
    }

    fun getAllFoods(): List<FoodDto> = withConn { conn ->
        conn.createStatement().use { st ->
            st.executeQuery("SELECT yemek_id, yemek_adi, yemek_resim_adi, yemek_fiyat FROM foods ORDER BY yemek_id ASC").use { rs ->
                val list = mutableListOf<FoodDto>()
                while (rs.next()) {
                    list.add(
                        FoodDto(
                            id = rs.getInt("yemek_id"),
                            name = rs.getString("yemek_adi"),
                            imageUrl = rs.getString("yemek_resim_adi"),
                            price = rs.getInt("yemek_fiyat")
                        )
                    )
                }
                list
            }
        }
    }

    fun getCartFoods(username: String): List<CartFoodDto> = withConn { conn ->
        conn.prepareStatement(
            """
            SELECT sepet_yemek_id, yemek_adi, yemek_resim_adi, yemek_fiyat, yemek_siparis_adet, kullanici_adi
            FROM cart_items
            WHERE kullanici_adi = ?
            ORDER BY sepet_yemek_id ASC
            """.trimIndent()
        ).use { ps ->
            ps.setString(1, username)
            ps.executeQuery().use { rs ->
                val list = mutableListOf<CartFoodDto>()
                while (rs.next()) {
                    list.add(
                        CartFoodDto(
                            cartId = rs.getInt("sepet_yemek_id"),
                            name = rs.getString("yemek_adi"),
                            imageUrl = rs.getString("yemek_resim_adi"),
                            price = rs.getInt("yemek_fiyat"),
                            quantity = rs.getInt("yemek_siparis_adet"),
                            username = rs.getString("kullanici_adi")
                        )
                    )
                }
                list
            }
        }
    }

    fun addToCart(name: String, imageUrl: String, price: Int, quantity: Int, username: String): Boolean = withConn { conn ->
        if (quantity !in 1..100 || price <= 0 || name.isBlank()) return@withConn false
        conn.prepareStatement(
            """
            INSERT INTO cart_items (yemek_adi, yemek_resim_adi, yemek_fiyat, yemek_siparis_adet, kullanici_adi)
            VALUES (?, ?, ?, ?, ?)
            ON CONFLICT(kullanici_adi, yemek_adi) DO UPDATE SET
                yemek_siparis_adet = MIN(100, cart_items.yemek_siparis_adet + excluded.yemek_siparis_adet),
                yemek_fiyat = excluded.yemek_fiyat,
                yemek_resim_adi = excluded.yemek_resim_adi
            """.trimIndent()
        ).use { ps ->
            ps.setString(1, name.trim())
            ps.setString(2, imageUrl.trim())
            ps.setInt(3, price)
            ps.setInt(4, quantity)
            ps.setString(5, username)
            ps.executeUpdate() > 0
        }
    }

    fun updateCartQuantity(cartId: Int, username: String, newQuantity: Int): Boolean = withConn { conn ->
        if (newQuantity <= 0) {
            removeFromCartInternal(conn, cartId, username)
        } else {
            val safeQty = newQuantity.coerceAtMost(100)
            conn.prepareStatement(
                "UPDATE cart_items SET yemek_siparis_adet = ? WHERE sepet_yemek_id = ? AND kullanici_adi = ?"
            ).use { ps ->
                ps.setInt(1, safeQty)
                ps.setInt(2, cartId)
                ps.setString(3, username)
                ps.executeUpdate() > 0
            }
        }
    }

    fun removeFromCart(cartId: Int, username: String): Boolean = withConn { conn ->
        removeFromCartInternal(conn, cartId, username)
    }

    private fun removeFromCartInternal(conn: Connection, cartId: Int, username: String): Boolean {
        return conn.prepareStatement(
            "DELETE FROM cart_items WHERE sepet_yemek_id = ? AND kullanici_adi = ?"
        ).use { ps ->
            ps.setInt(1, cartId)
            ps.setString(2, username)
            ps.executeUpdate() > 0
        }
    }

    fun checkout(username: String): Boolean = withConn { conn ->
        conn.autoCommit = false
        try {
            var totalItems = 0
            var totalPrice = 0
            conn.prepareStatement(
                "SELECT COALESCE(SUM(yemek_siparis_adet), 0), COALESCE(SUM(yemek_siparis_adet * yemek_fiyat), 0) FROM cart_items WHERE kullanici_adi = ?"
            ).use { ps ->
                ps.setString(1, username)
                ps.executeQuery().use { rs ->
                    if (rs.next()) {
                        totalItems = rs.getInt(1)
                        totalPrice = rs.getInt(2)
                    }
                }
            }
            if (totalItems <= 0) {
                conn.rollback()
                return@withConn false
            }
            conn.prepareStatement(
                "INSERT INTO orders (kullanici_adi, total_items, total_price) VALUES (?, ?, ?)"
            ).use { ps ->
                ps.setString(1, username)
                ps.setInt(2, totalItems)
                ps.setInt(3, totalPrice)
                ps.executeUpdate()
            }
            conn.prepareStatement("DELETE FROM cart_items WHERE kullanici_adi = ?").use { ps ->
                ps.setString(1, username)
                ps.executeUpdate()
            }
            conn.commit()
            true
        } catch (e: Exception) {
            conn.rollback()
            throw e
        } finally {
            conn.autoCommit = true
        }
    }
}
