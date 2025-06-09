package com.proyek.leaf_in.data.repository

import com.proyek.leaf_in.data.model.MenuItem
// Import service API Anda di sini, contoh:
// import com.proyek.leaf_in.data.network.ApiService // Anggap ada ApiService

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MenuRepository @Inject constructor(
    // private val apiService: ApiService // Uncomment jika sudah ada ApiService
) {
    // Fungsi ini akan memanggil API atau database untuk mendapatkan data menu
    suspend fun getAllMenuItems(): List<MenuItem> {
        // TODO: Implement actual API call here
        // Untuk sementara, kita pakai data dummy
        return listOf(
            MenuItem(id = "1", name = "Toast", imageUrl = "url_toast", price = 25000.0, category = "Meals"),
            MenuItem(id = "2", name = "Bubur Ayam", imageUrl = "url_bubur", price = 15000.0, category = "Meals"),
            MenuItem(id = "3", name = "Salad", imageUrl = "url_salad", price = 40000.0, category = "Meals"),
            MenuItem(id = "4", name = "Smoothies", imageUrl = "url_smoothies", price = 25000.0, category = "Beverages"),
            MenuItem(id = "5", name = "Jamu Beras Kencur", imageUrl = "url_jamu", price = 8000.0, category = "Beverages"),
            MenuItem(id = "6", name = "Green Tea", imageUrl = "url_greentea", price = 12000.0, category = "Beverages"),
        )
        // return apiService.getMenuItems() // Contoh jika menggunakan Retrofit
    }
}