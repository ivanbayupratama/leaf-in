package com.proyek.leaf_in.data.repository

import com.proyek.leaf_in.data.model.MenuItem
import com.proyek.leaf_in.data.remote.api.ApiService
import com.proyek.leaf_in.data.local.dao.MenuItemDao // <<< Pastikan ini diimpor
import com.proyek.leaf_in.data.local.entities.MenuItemEntity // <<< Pastikan ini diimpor
import kotlinx.coroutines.flow.Flow // <<< Pastikan ini diimpor
import kotlinx.coroutines.flow.map // <<< Pastikan ini diimpor
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MenuRepository @Inject constructor(
    private val apiService: ApiService,
    private val menuItemDao: MenuItemDao // <<< Pastikan ini disuntikkan
) {
    /**
     * Mengambil semua produk dari database lokal (Room) sebagai Flow.
     * Ini akan otomatis update setiap kali data di Room berubah.
     */
    fun getAllProducts(): Flow<List<MenuItem>> { // <<< Sekarang mengembalikan Flow
        return menuItemDao.getAllMenuItems().map { entities ->
            entities.map { it.toMenuItem() } // Konversi dari Entity Room ke Model API
        }
    }

    /**
     * Memuat ulang data produk dari API dan menyimpannya ke database lokal (Room).
     * Ini harus dipanggil secara terpisah (misalnya, saat aplikasi dibuka atau pull-to-refresh).
     */
    suspend fun refreshProducts() { // <<< Fungsi baru untuk refresh dari API
        try {
            val apiMenuItems = apiService.getAllProducts()
            val entitiesToInsert = apiMenuItems.map { MenuItemEntity.fromMenuItem(it) }
            menuItemDao.insertMenuItems(entitiesToInsert) // Simpan ke Room
        } catch (e: Exception) {
            // Tangani error (misalnya, tidak ada koneksi internet saat refresh)
            // Penting untuk melempar error agar ViewModel bisa menanganinya dan menampilkan pesan ke UI.
            throw e
        }
    }

    /**
     * Menghapus semua item dari cache lokal. Berguna untuk fitur logout atau debug.
     */
    suspend fun clearCache() {
        menuItemDao.deleteAllMenuItems()
    }
}