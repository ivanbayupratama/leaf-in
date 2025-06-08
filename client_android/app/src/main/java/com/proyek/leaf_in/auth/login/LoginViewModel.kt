package com.proyek.leaf_in.auth.login // Ganti dengan nama package kamu

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class LoginViewModel : ViewModel() {

    // _uiState bersifat private agar hanya bisa diubah dari dalam ViewModel
    private val _uiState = MutableStateFlow(LoginUiState())
    // uiState diekspos ke UI agar bisa "dibaca" (read-only)
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    /**
     * Dipanggil setiap kali teks di field email berubah.
     */
    fun onEmailChange(newEmail: String) {
        _uiState.update { currentState ->
            currentState.copy(email = newEmail)
        }
    }

    /**
     * Dipanggil setiap kali teks di field password berubah.
     */
    fun onPasswordChange(newPassword: String) {
        _uiState.update { currentState ->
            currentState.copy(password = newPassword)
        }
    }

    /**
     * Dipanggil saat tombol Login diklik.
     * TODO: Tambahkan logika login di sini.
     */
    // Di dalam kelas LoginViewModel Anda

    fun onLoginClick() {
        // ...
        // Misalkan Anda melakukan validasi atau memanggil repository di sini
        // ...

        // --- KETIKA PROSES LOGIN BERHASIL ---
        // (misalnya setelah mendapat respons sukses dari API/Firebase)
        // Update state untuk menandakan login sukses
        _uiState.update { currentState ->
            currentState.copy(
                isLoginSuccess = true // <-- UBAH STATE DI SINI
            )
        }

        // --- JIKA GAGAL ---
        // Anda bisa meng-update state error
        // _uiState.update { currentState ->
        //     currentState.copy(loginError = "Email atau password salah")
        // }
    }

    /**
     * Dipanggil saat teks "Register" diklik.
     * TODO: Tambahkan logika navigasi ke halaman register.
     */
    fun onRegisterClick() {
        println("Navigate to Register screen")
    }

    /**
     * Dipanggil saat teks "Forgot password" diklik.
     * TODO: Tambahkan logika untuk lupa password.
     */
    fun onForgotPasswordClick() {
        println("Navigate to Forgot Password screen")
    }
}