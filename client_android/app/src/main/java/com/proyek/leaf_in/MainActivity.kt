package com.proyek.leaf_in

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.proyek.leaf_in.auth.login.LoginScreen
import com.proyek.leaf_in.auth.register.RegisterScreen
import com.proyek.leaf_in.ui.theme.Leaf_inTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Leaf_inTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // 1. Panggil AppNavigation di sini
                    AppNavigation()
                }
            }
        }
    }
}

@Composable
fun AppNavigation() {
    // 2. Membuat NavController yang akan mengelola semua navigasi
    val navController = rememberNavController()

    // 3. NavHost adalah container yang menampilkan layar berdasarkan rute saat ini
    NavHost(
        navController = navController,
        startDestination = "login" // Menentukan layar pertama yang muncul
    ) {
        // 4. Mendefinisikan rute untuk layar Login
        composable(route = "login") {
            LoginScreen(
                onLoginSuccess = {
                    // Jika login sukses, pindah ke 'home' dan hapus riwayat 'login'
                    // agar pengguna tidak bisa kembali ke halaman login dengan tombol back.
                    navController.navigate("home") {
                        popUpTo("login") { inclusive = true }
                    }
                },
                onNavigateToRegister = {
                    // Pindah ke halaman register
                    navController.navigate("register")
                }
            )
        }

        // 5. Mendefinisikan rute untuk layar Register
        composable(route = "register") {
            RegisterScreen(
                onRegistrationSuccess = {
                    // Jika registrasi sukses, kembali ke halaman sebelumnya (login)
                    navController.popBackStack()
                },
                // Tambahan: jika ada tombol kembali di halaman register
                onNavigateBackToLogin = {
                    navController.popBackStack()
                }
            )
        }

        // 6. Mendefinisikan rute untuk layar Home (sebagai contoh)
        composable(route = "home") {
            // Ini hanya layar contoh setelah login berhasil
            HomeScreenPlaceholder()
        }
    }
}

@Composable
fun HomeScreenPlaceholder() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("Selamat Datang! Anda Berhasil Login.")
    }
}