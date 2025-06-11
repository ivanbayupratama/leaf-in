package com.proyek.leaf_in.server

import com.proyek.leaf_in.server.plugins.configureRouting
import com.proyek.leaf_in.server.plugins.configureSerialization
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.example.com.proyek.leaf_in.server.data.DatabaseFactory

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    DatabaseFactory.init() // Inisialisasi Database
    configureSerialization()
    configureRouting()
}