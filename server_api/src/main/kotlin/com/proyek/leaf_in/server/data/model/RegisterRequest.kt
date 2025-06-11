package com.proyek.leaf_in.server.data.model
import kotlinx.serialization.Serializable

@Serializable
data class RegisterRequest(
    val fullName: String,
    val email: String,
    val password: String,
    val confirmPassword: String
)