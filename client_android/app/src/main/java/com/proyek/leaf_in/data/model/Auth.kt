package com.proyek.leaf_in.data.model // Sesuaikan dengan package-mu

/**
 * Cetakan untuk data yang DIKIRIM ke server saat login/register
 */
data class AuthRequest(
    val fullName: String? = null, // Dibuat opsional, karena login tidak butuh ini
    val email: String,
    val password: String
)

/**
 * Cetakan untuk data yang DITERIMA dari server setelah login/register
 */
data class AuthResponse(
    val message: String? = null, // <<< PERBAIKI INI: Jadikan String? (nullable)
    val token: String? = null    // Tetap String?
)