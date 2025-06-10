package com.proyek.leaf_in.auth.login // Atau package presentation.auth Anda

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.proyek.leaf_in.data.model.AuthRequest
import com.proyek.leaf_in.data.model.AuthResponse
import com.proyek.leaf_in.data.repository.AuthRepository
import com.proyek.leaf_in.data.local.UserPreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun onEmailChange(email: String) {
        _uiState.update { it.copy(email = email, loginError = null) }
    }

    fun onPasswordChange(password: String) {
        _uiState.update { it.copy(password = password, loginError = null) }
    }

    fun onLoginClick() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, loginError = null) }
            try {
                val request = AuthRequest(
                    email = _uiState.value.email,
                    password = _uiState.value.password
                )
                val response = authRepository.loginUser(request)

                // PERBAIKI INI: Gunakan isNullOrEmpty() untuk String?
                if (!response.token.isNullOrEmpty()) {
                    userPreferencesRepository.saveAuthToken(response.token)
                    _uiState.update { it.copy(isLoading = false, isLoginSuccess = true) }
                } else {
                    // PERBAIKI INI: Sekarang response.message adalah String?, Elvis operator berfungsi benar
                    _uiState.update { it.copy(isLoading = false, loginError = response.message ?: "Login gagal: Email atau password salah.") }
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, loginError = e.message ?: "Terjadi kesalahan koneksi atau server.") }
            }
        }
    }

    fun onForgotPasswordClick() {
        // TODO: Implement navigasi ke forgot password
    }

    fun errorShown() {
        _uiState.update { it.copy(loginError = null) }
    }
}