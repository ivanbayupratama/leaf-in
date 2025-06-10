package com.proyek.leaf_in.server.services

import com.proyek.leaf_in.server.data.model.ProductResponse
import com.proyek.leaf_in.server.data.model.Products
import org.example.com.proyek.leaf_in.server.data.DatabaseFactory.dbQuery
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.selectAll

class ProductService {
    suspend fun getAllProducts(): List<ProductResponse> = dbQuery {
        Products.selectAll().map { toProductResponse(it) }
    }

    private fun toProductResponse(row: ResultRow) = ProductResponse(
        id = row[Products.id],
        name = row[Products.name],
        description = row[Products.description], // Exposed akan handle null secara otomatis
        price = row[Products.price],
        imageUrl = row[Products.imageUrl] // Exposed akan handle null secara otomatis
    )
}