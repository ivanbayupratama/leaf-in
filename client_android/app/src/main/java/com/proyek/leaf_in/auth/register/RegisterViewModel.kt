package com.proyek.leaf_in.auth.register

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RegisterViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState = _uiState.asStateFlow()

    fun onFullNameChange(fullName: String) {
        _uiState.update { it.copy(fullName = fullName) }
    }

    fun onEmailChange(email: String) {
        _uiState.update { it.copy(email = email) }
    }

    fun onPasswordChange(password: String) {
        _uiState.update { it.copy(password = password) }
    }

    fun onConfirmPasswordChange(confirmPassword: String) {
        _uiState.update { it.copy(confirmPassword = confirmPassword) }
    }

    fun register() {
        viewModelScope.launch {
            val currentState = _uiState.value
            // 1. Lakukan Validasi
            if (currentState.fullName.isBlank() || currentState.email.isBlank() || currentState.password.isBlank()) {
                _uiState.update { it.copy(registrationError = "Semua field harus diisi") }
                return@launch
            }
            if (!Patterns.EMAIL_ADDRESS.matcher(currentState.email).matches()) {
                _uiState.update { it.copy(registrationError = "Format email tidak valid") }
                return@launch
            }
            if (currentState.password.length < 8) {
                _uiState.update { it.copy(registrationError = "Password minimal 8 karakter") }
                return@launch
            }
            if (currentState.password != currentState.confirmPassword) {
                _uiState.update { it.copy(registrationError = "Password dan konfirmasi tidak cocok") }
                return@launch
            }

            // 2. Jika validasi berhasil, mulai proses registrasi
            _uiState.update { it.copy(isLoading = true, registrationError = null) }

            // 3. Simulasi pemanggilan API/Database
            try {
                delay(2000) // Simulasi delay jaringan selama 2 detik
                // TODO: Ganti bagian ini dengan logika registrasi sesungguhnya (API call, simpan ke database, dll)

                // Jika berhasil
                _uiState.update { it.copy(isLoading = false, isRegistrationSuccess = true) }

            } catch (e: Exception) {
                // Jika gagal (contoh: tidak ada internet)
                _uiState.update { it.copy(isLoading = false, registrationError = "Registrasi gagal: ${e.message}") }
            }
        }
    }

    /**
     * Panggil fungsi ini setelah pesan error ditampilkan di UI,
     * agar error tidak muncul lagi saat UI recompose (misal: saat rotasi layar).
     */
    fun errorShown() {
        _uiState.update { it.copy(registrationError = null) }
    }
}