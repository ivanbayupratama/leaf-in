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
// HAPUS IMPORT INI: import java.util.prefs.Preferences // <<< INI SALAH!
import javax.inject.Inject
import javax.inject.Singleton

// Buat DataStore instance di top-level (di luar kelas)
// Pastikan nama file ini unik di proyek Anda jika ada file lain dengan nama yang sama.
// Misalnya, jika ada Context.kt lain, Anda bisa pindahkan ini ke file terpisah
// atau pastikan tidak ada konflik nama.
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_prefs")


@Singleton
class UserPreferencesRepository @Inject constructor(
    @ApplicationContext private val context: Context
) {
    // Kunci untuk menyimpan token autentikasi
    private object PreferencesKeys {
        val AUTH_TOKEN = stringPreferencesKey("auth_token")
    }

    // Ambil token dari DataStore
    val authToken: Flow<String?> = context.dataStore.data
        .map { preferences ->
            preferences[PreferencesKeys.AUTH_TOKEN]
        }

    // Simpan token ke DataStore
    suspend fun saveAuthToken(token: String) {
        context.dataStore.edit { preferences -> // Perhatikan: gunakan context.datastore di sini
            preferences[PreferencesKeys.AUTH_TOKEN] = token
        }
    }

    // Hapus token dari DataStore (saat logout)
    suspend fun clearAuthToken() {
        context.dataStore.edit { preferences -> // Perhatikan: gunakan context.datastore di sini
            preferences.remove(PreferencesKeys.AUTH_TOKEN)
        }
    }
}