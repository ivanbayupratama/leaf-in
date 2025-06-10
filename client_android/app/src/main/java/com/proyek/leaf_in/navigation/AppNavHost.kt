package com.proyek.leaf_in.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.proyek.leaf_in.auth.login.LoginScreen
import com.proyek.leaf_in.auth.register.RegisterScreen
import com.proyek.leaf_in.home.HomeScreen

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    startDestination: String = Screen.Login.route // Aplikasi dimulai dari halaman Login
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
                    // Jika login berhasil, pindah ke Home dan hapus halaman login dari history
                    // agar pengguna tidak bisa kembali ke halaman login dengan tombol back.
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Login.route) {
                            inclusive = true
                        }
                    }
                },
                onNavigateToRegister = {
                    // Pindah ke halaman Register
                    navController.navigate(Screen.Register.route)
                }
            )
        }

        // Rute untuk Halaman Register
        composable(Screen.Register.route) {
            RegisterScreen(
                onRegistrationSuccess = {
                    // Jika registrasi berhasil, kembali ke halaman sebelumnya (Login)
                    navController.popBackStack()
                },
                // Kita beri nama onNavigateToLogin agar lebih jelas
                onNavigateBackToLogin = {
                    // Kembali ke halaman sebelumnya (Login)
                    navController.popBackStack()
                }
            )
        }

        // Rute untuk Halaman Home (yang ada preview Meals & Beverages)
        composable(Screen.Home.route) {
            // Kita panggil HomeScreen yang sudah kita buat
            HomeScreen(navController = navController)
        }

        // Rute untuk Halaman Daftar Produk ("Show All")

    }
}