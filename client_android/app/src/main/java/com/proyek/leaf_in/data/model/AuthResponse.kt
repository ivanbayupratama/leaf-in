package com.proyek.leaf_in.data.model // Sesuaikan dengan package-mu

/**
 * Cetakan untuk data yang DIKIRIM ke server saat login/register
 */

/**
 * Cetakan untuk data yang DITERIMA dari server setelah login/register
 */
data class AuthResponse(
    val message: String? = null, // <<< PERBAIKI INI: Jadikan String? (nullable)
    val token: String? = null    // Tetap String?
)