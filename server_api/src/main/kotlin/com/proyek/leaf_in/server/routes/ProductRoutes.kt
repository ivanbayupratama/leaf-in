package com.proyek.leaf_in.server.routes

import com.proyek.leaf_in.server.services.ProductService
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.productRoutes() {
    val productService = ProductService()

    get("/products") {
        val products = productService.getAllProducts()
        call.respond(products)
    }
}