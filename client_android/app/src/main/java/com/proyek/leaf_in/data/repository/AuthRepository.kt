package com.proyek.leaf_in.data.repository

import com.proyek.leaf_in.data.model.LoginRequest
import com.proyek.leaf_in.data.model.RegisterRequest
import com.proyek.leaf_in.data.model.AuthResponse
import com.proyek.leaf_in.data.remote.api.ApiService
import javax.inject.Inject
import javax.inject.Singleton

// @Singleton memastikan hanya ada satu instance dari AuthRepository di seluruh aplikasi
@Singleton
class AuthRepository @Inject constructor(
    private val apiService: ApiService // Hilt akan menyediakan instance ApiService di sini
) {
    // Fungsi untuk mendaftarkan pengguna, memanggil API register
    suspend fun registerUser(request: RegisterRequest): AuthResponse {
        return apiService.registerUser(request)
    }

    // Fungsi untuk login pengguna, memanggil API login
    suspend fun loginUser(request: LoginRequest): AuthResponse {
        return apiService.loginUser(request)
    }
}