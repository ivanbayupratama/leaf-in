package com.proyek.leaf_in.server.data.model
import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(val email: String, val password: String)