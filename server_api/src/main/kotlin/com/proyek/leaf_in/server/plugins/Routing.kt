package com.proyek.leaf_in.server.plugins

import com.proyek.leaf_in.server.routes.authRoutes
import com.proyek.leaf_in.server.routes.productRoutes
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        authRoutes()
        productRoutes()
    }
}