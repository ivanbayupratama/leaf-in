package com.proyek.leaf_in.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.proyek.leaf_in.auth.login.LoginScreen
import com.proyek.leaf_in.auth.register.RegisterScreen
import com.proyek.leaf_in.cart.CartScreen // <-- 1. IMPORT CartScreen
import com.proyek.leaf_in.home.HomeScreen
import com.proyek.leaf_in.meals.MealsScreen

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    startDestination: String = Screen.Login.route
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        // Rute untuk Halaman Login
        composable(Screen.Login.route) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Login.route) {
                            inclusive = true
                        }
                    }
                },
                onNavigateToRegister = {
                    navController.navigate(Screen.Register.route)
                }
            )
        }

        // Rute untuk Halaman Register
        composable(Screen.Register.route) {
            RegisterScreen(
                onRegistrationSuccess = {
                    navController.popBackStack()
                },
                onNavigateBackToLogin = {
                    navController.popBackStack()
                }
            )
        }

        // Rute untuk Halaman Home
        composable(Screen.Home.route) {
            HomeScreen(navController = navController)
        }

        // Rute untuk Halaman Daftar Produk ("Show All")
        composable(
            route = Screen.ProductList.route,
            arguments = listOf(navArgument("category") { type = NavType.StringType })
        ) { backStackEntry ->
            val category = backStackEntry.arguments?.getString("category")

            when (category) {
                "meals" -> {
                    MealsScreen(navController = navController)
                }
                "beverages" -> {
                    // TODO: Panggil BeveragesScreen di sini jika sudah dibuat
                }
            }
        }

        // --- 2. TAMBAHKAN RUTE UNTUK HALAMAN CART ---
        composable(Screen.Cart.route) {
            CartScreen(
                onBackClicked = { navController.popBackStack() },
                onCheckoutClicked = {
                    // TODO: Navigasi ke halaman pembayaran
                }
            )
        }

        // --- 3. TAMBAHKAN RUTE UNTUK HALAMAN PROFILE ---
        composable(Screen.Profile.route) {
            // Untuk sementara, kita tampilkan halaman placeholder
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Halaman Profil")
            }
        }
    }
}