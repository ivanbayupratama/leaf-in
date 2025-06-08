// Berkas: a/a/a/feature/auth/register/RegisterUiState.kt

package com.proyek.leaf_in.auth.register

/**
 * Merepresentasikan semua state yang ada di halaman registrasi.
 *
 * @property fullName Nilai dari input field nama lengkap.
 * @property email Nilai dari input field email.
 * @property password Nilai dari input field password.
 * @property confirmPassword Nilai dari input field konfirmasi password.
 * @property isLoading Menandakan apakah proses registrasi sedang berjalan (untuk menampilkan loading).
 * @property registrationError Pesan error jika registrasi gagal, null jika tidak ada error.
 * @property isRegistrationSuccess Menandakan jika registrasi berhasil (untuk navigasi ke halaman lain).
 */

data class RegisterUiState(
    val fullName: String = "",
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val isLoading: Boolean = false,
    val registrationError: String? = null,
    val isRegistrationSuccess: Boolean = false
)