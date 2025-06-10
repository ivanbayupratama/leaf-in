package com.proyek.leaf_in.server.routes

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.productRoutes() {
    route("/products") {
        get {
            call.respondText("Ini adalah daftar produk (placeholder)")
        }
    }
}