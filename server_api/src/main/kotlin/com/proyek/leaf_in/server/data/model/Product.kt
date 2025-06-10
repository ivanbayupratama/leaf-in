package com.proyek.leaf_in.server.data.model

import org.jetbrains.exposed.sql.Table
import kotlinx.serialization.Serializable

object Products : Table() {
    val id = varchar("id", 50)
    val name = varchar("name", 255)
    val description = text("description").nullable() // <-- UBAH DI SINI
    val price = integer("price")
    val imageUrl = varchar("image_url", 255).nullable() // <-- UBAH DI SINI
    override val primaryKey = PrimaryKey(id)
}

@Serializable
data class ProductResponse(
    val id: String,
    val name: String,
    val description: String?, // <-- UBAH DI SINI
    val price: Int,
    val imageUrl: String? // <-- UBAH DI SINI
)