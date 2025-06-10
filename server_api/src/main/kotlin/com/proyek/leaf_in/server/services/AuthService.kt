package com.proyek.leaf_in.server.services

import at.favre.lib.crypto.bcrypt.BCrypt
import com.proyek.leaf_in.server.data.model.AuthRequest
import com.proyek.leaf_in.server.data.model.AuthResponse
import com.proyek.leaf_in.server.data.model.Users
import org.example.com.proyek.leaf_in.server.data.DatabaseFactory.dbQuery
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import java.util.*

class AuthService {

    suspend fun registerUser(request: AuthRequest): AuthResponse {
        // Cek dulu apakah email sudah terdaftar
        val user = dbQuery {
            Users.select { Users.email eq request.email }.singleOrNull()
        }

        if (user != null) {
            return AuthResponse(message = "Email sudah terdaftar")
        }

        // Hash password sebelum disimpan
        val hashedPassword = BCrypt.withDefaults().hashToString(12, request.password.toCharArray())

        // Simpan pengguna baru ke database
        dbQuery {
            Users.insert {
                it[id] = "user-${UUID.randomUUID()}"
                it[fullName] = request.fullName ?: ""
                it[email] = request.email
                it[password] = hashedPassword
            }
        }

        return AuthResponse(message = "Registrasi berhasil!", token = null)
    }

    suspend fun loginUser(request: AuthRequest): AuthResponse {
        // Cari pengguna berdasarkan email
        val user = dbQuery {
            Users.select { Users.email eq request.email }.singleOrNull()
        } ?: return AuthResponse(message = "Email atau password salah") // Jika user tidak ditemukan

        // Verifikasi password
        val result = BCrypt.verifyer().verify(request.password.toCharArray(), user[Users.password])

        return if (result.verified) {
            AuthResponse(message = "Login sukses!", token = "ini-adalah-token-bohongan-${UUID.randomUUID()}")
        } else {
            AuthResponse(message = "Email atau password salah")
        }
    }
}