package com.proyek.leaf_in.server.data.model

import org.jetbrains.exposed.sql.Table
import kotlinx.serialization.Serializable

object Products : Table() {
    val id = varchar("id", 50)
    val name = varchar("name", 255)
    val category = varchar("category", 255) // <-- UBAH DI SINI
    val price = integer("price")
    val imageResName = varchar("image_res_name", 255).nullable()
    override val primaryKey = PrimaryKey(id)
}

@Serializable
data class ProductResponse(
    val id: String,
    val name: String,
    val category: String, // <-- UBAH DI SINI
    val price: Int,
    val imageResName: String? // <-- UBAH DI SINI
)