package com.proyek.leaf_in.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

// Deklarasi extension property di top-level untuk membuat instance DataStore.
// Ini memastikan hanya ada satu instance dengan nama "user_preferences" di seluruh aplikasi.
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_preferences")

@Singleton
class UserPreferencesRepository @Inject constructor(
    // Hilt akan menyediakan ApplicationContext di sini
    @ApplicationContext private val context: Context
) {

    // Kunci-kunci yang digunakan untuk mengakses nilai di dalam DataStore.
    // Menggunakan object privat memastikan kunci-kunci ini tidak bisa diakses dari luar.
    private object PreferencesKeys {
        val AUTH_TOKEN = stringPreferencesKey("auth_token")
        val USER_EMAIL = stringPreferencesKey("user_email")
    }

    // Flow untuk mengambil token autentikasi.
    // UI atau ViewModel bisa mengamati (collect) Flow ini untuk mendapatkan update token secara real-time.
    val authToken: Flow<String?> = context.dataStore.data
        .map { preferences ->
            preferences[PreferencesKeys.AUTH_TOKEN]
        }

    // Flow untuk mengambil email pengguna yang sedang login.
    // Berguna untuk fitur checkout dan menampilkan info user.
    val userEmail: Flow<String?> = context.dataStore.data
        .map { preferences ->
            preferences[PreferencesKeys.USER_EMAIL]
        }

    /**
     * Menyimpan token autentikasi ke DataStore.
     * Dipanggil setelah login berhasil.
     */
    suspend fun saveAuthToken(token: String) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.AUTH_TOKEN] = token
        }
    }

    /**
     * Menyimpan email pengguna ke DataStore.
     * Dipanggil bersamaan dengan saveAuthToken setelah login berhasil.
     */
    suspend fun saveUserEmail(email: String) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.USER_EMAIL] = email
        }
    }

    /**
     * Menghapus semua preferensi pengguna dari DataStore.
     * Sangat berguna untuk diimplementasikan pada fitur logout.
     */
    suspend fun clearAll() {
        context.dataStore.edit { preferences ->
            preferences.clear()
        }
    }
}