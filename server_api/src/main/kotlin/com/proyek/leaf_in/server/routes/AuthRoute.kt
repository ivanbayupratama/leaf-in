package com.proyek.leaf_in.server.routes

import com.proyek.leaf_in.server.data.model.AuthRequest
import com.proyek.leaf_in.server.services.AuthService
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.authRoutes() {
    val authService = AuthService()

    post("/register") {
        val request = call.receive<AuthRequest>()
        val response = authService.registerUser(request)
        call.respond(response)
    }

    post("/login") {
        val request = call.receive<AuthRequest>()
        val response = authService.loginUser(request)
        call.respond(response)
    }
}