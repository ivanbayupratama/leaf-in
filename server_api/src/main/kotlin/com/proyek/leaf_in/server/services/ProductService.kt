package com.proyek.leaf_in.server.services

import com.proyek.leaf_in.server.data.DatabaseFactory.dbQuery
import com.proyek.leaf_in.server.data.model.ProductResponse
import com.proyek.leaf_in.server.data.model.Products
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.selectAll

class ProductService {
    suspend fun getAllProducts(): List<ProductResponse> = dbQuery {
        Products.selectAll().map { toProductResponse(it) }
    }

    private fun toProductResponse(row: ResultRow) = ProductResponse(
        id = row[Products.id],
        name = row[Products.name],
        category = row[Products.category],
        price = row[Products.price],
        imageResName = row[Products.imageResName] // <-- DIUBAH DARI imageUrl
    )
}