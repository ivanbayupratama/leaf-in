package com.proyek.leaf_in.server.routes

import com.proyek.leaf_in.server.data.model.CreateOrderRequest
import com.proyek.leaf_in.server.data.model.LoginRequest
import com.proyek.leaf_in.server.data.model.RegisterRequest
import com.proyek.leaf_in.server.services.AuthService
import com.proyek.leaf_in.server.services.OrderService
import io.ktor.http.* // Import HttpStatusCode untuk penanganan error
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.authRoutes() {
    val authService = AuthService()
    val orderService = OrderService()

    // --- Rute Registrasi (Tidak Berubah) ---
    post("/register") {
        val request = call.receive<RegisterRequest>()
        val response = authService.registerUser(request)
        call.respond(response)
    }

    // --- Rute Login (Tidak Berubah) ---
    post("/login") {
        val request = call.receive<LoginRequest>()
        val response = authService.loginUser(request)
        call.respond(response)
    }

    // --- Rute Checkout (SUDAH DIKONDISIKAN) ---
    post("/checkout") {
        try {

            val request = call.receive<CreateOrderRequest>()

            val response = orderService.createOrder(request)

            call.respond(response)

        } catch (e: Exception) {
            // Penanganan error dasar jika terjadi masalah (misal: JSON tidak valid)
            call.respond(HttpStatusCode.InternalServerError, mapOf("error" to (e.localizedMessage ?: "An unknown error occurred")))
        }
    }
}