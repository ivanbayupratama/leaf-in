package com.proyek.leaf_in.auth.register

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.proyek.leaf_in.data.model.AuthRequest
import com.proyek.leaf_in.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.proyek.leaf_in.di.IoDispatcher // Pastikan import IoDispatcher

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher // Jika Anda menggunakannya di sini
) : ViewModel() {

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
        viewModelScope.launch(ioDispatcher) { // Pastikan menggunakan ioDispatcher jika operasi ini I/O
            val currentState = _uiState.value
            // 1. Lakukan Validasi
            if (currentState.fullName.isBlank() || currentState.email.isBlank() || currentState.password.isBlank()) {
                _uiState.update { it.copy(registrationError = "Semua field harus diisi", isLoading = false) }
                return@launch
            }
            if (!Patterns.EMAIL_ADDRESS.matcher(currentState.email).matches()) {
                _uiState.update { it.copy(registrationError = "Format email tidak valid", isLoading = false) }
                return@launch
            }
            if (currentState.password.length < 8) {
                _uiState.update { it.copy(registrationError = "Password minimal 8 karakter", isLoading = false) }
                return@launch
            }
            if (currentState.password != currentState.confirmPassword) {
                _uiState.update { it.copy(registrationError = "Password dan konfirmasi tidak cocok", isLoading = false) }
                return@launch
            }

            // 2. Jika validasi berhasil, mulai proses registrasi
            _uiState.update { it.copy(isLoading = true, registrationError = null) }

            // 3. Panggil API Registrasi
            try {
                // PERBAIKI INI: Gunakan named parameters untuk AuthRequest karena fullName opsional
                val request = AuthRequest(
                    fullName = currentState.fullName,
                    email = currentState.email,
                    password = currentState.password
                )
                val response = authRepository.registerUser(request)

                // PERBAIKI INI: Gunakan isNullOrEmpty() untuk String?
                if (!response.token.isNullOrEmpty()) {
                    // Jika register otomatis login dan mengembalikan token, simpan di sini
                    // userPreferencesRepository.saveAuthToken(response.token) // Jika ada UserPreferencesRepository
                    _uiState.update { it.copy(isLoading = false, isRegistrationSuccess = true) }
                } else {
                    // PERBAIKI INI: Sekarang response.message adalah String?, Elvis operator berfungsi benar
                    _uiState.update { it.copy(isLoading = false, registrationError = response.message ?: "Registrasi gagal.") }
                }

            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, registrationError = e.message ?: "Terjadi kesalahan koneksi atau server.") }
            }
        }
    }

    fun errorShown() {
        _uiState.update { it.copy(registrationError = null) }
    }
}