package com.proyek.leaf_in.auth.login
/**
 * Data class yang merepresentasikan semua state untuk Login Screen.
 */
data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val loginError: String? = null,
    val isLoginSuccess: Boolean = false // <-- TAMBAHKAN BARIS INI
)