package com.proyek.leaf_in.server.data.model

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.datetime

/**
 * Mendefinisikan tabel `orders` di database.
 * Nama tabel secara eksplisit diatur ke "orders" untuk konsistensi.
 */
object Orders : Table("orders") { // <-- Penyesuaian: nama tabel eksplisit
    val id = varchar("id", 50)
    val userId = varchar("user_id", 50).references(Users.id)
    val totalPrice = integer("total_price")
    val orderDate = datetime("order_date").nullable()

    override val primaryKey = PrimaryKey(id)
}

/**
 * Mendefinisikan tabel `order_items` di database.
 * Nama tabel secara eksplisit diatur ke "order_items" untuk konsistensi.
 */
object OrderItems : Table("order_items") { // <-- Penyesuaian: nama tabel eksplisit
    val id = varchar("id", 50)
    val orderId = varchar("order_id", 50).references(Orders.id)
    val productId = varchar("product_id", 50).references(Products.id)
    val quantity = integer("quantity")
    val pricePerItem = integer("price_per_item")

    override val primaryKey = PrimaryKey(id)
}

// =========================================================================
// Data Class untuk Komunikasi API (Request & Response)
// =========================================================================

/**
 * Model data yang dikirim dari aplikasi klien saat melakukan checkout.
 */
@Serializable
data class CreateOrderRequest(
    val userEmail: String,
    val items: List<OrderItemRequest>
)

/**
 * Model data untuk setiap item di dalam keranjang belanja yang dikirim oleh klien.
 */
@Serializable
data class OrderItemRequest(
    val productId: String,
    val quantity: Int
)

/**
 * Model respons umum yang hanya berisi pesan.
 */
@Serializable
data class GenericResponse(
    val message: String
)