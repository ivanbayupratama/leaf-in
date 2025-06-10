package com.proyek.leaf_in.server.data.model
import kotlinx.serialization.Serializable

@Serializable
data class AuthRequest(val fullName: String?, val email: String, val password: String)
@Serializable
data class AuthResponse(val message: String, val token: String? = null)