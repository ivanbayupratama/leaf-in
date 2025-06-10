package com.proyek.leaf_in.navigation

sealed class Screen(val route: String) {
    // Rute untuk Alur Otentikasi
    object Login : Screen("login_screen")
    object Register : Screen("register_screen")

    // Rute untuk Halaman Utama (Bottom Navigation)
    object Home : Screen("home_screen")
    object Cart : Screen("cart_screen")
    object Profile : Screen("profile_screen")

    // Rute untuk Halaman Lain
    object ProductList : Screen("product_list/{category}") {
        fun createRoute(category: String) = "product_list/$category"
    }
}