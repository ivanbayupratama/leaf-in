// path: com/proyek/leaf_in/MainActivity.kt

package com.proyek.leaf_in

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.proyek.leaf_in.auth.login.LoginScreen
import com.proyek.leaf_in.auth.register.RegisterScreen
import com.proyek.leaf_in.chart.ChartScreen // <-- 1. IMPORT ChartScreen
import com.proyek.leaf_in.home.HomeScreen
import com.proyek.leaf_in.ui.theme.Leaf_inTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Leaf_inTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavigation()
                }
            }
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "login" // Atau "home" jika mau langsung ke home untuk testing
    ) {
        composable(route = "login") {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate("home") {
                        popUpTo("login") { inclusive = true }
                    }
                },
                onNavigateToRegister = {
                    navController.navigate("register")
                }
            )
        }
        composable(route = "register") {
            RegisterScreen(
                onRegistrationSuccess = { navController.popBackStack() },
                onNavigateBackToLogin = { navController.popBackStack() }
            )
        }
        composable(route = "home") {
            // 2. PASS NavController KE HomeScreen
            HomeScreen(navController = navController)
        }

        // 3. TAMBAHKAN RUTE BARU UNTUK CHART
        composable(route = "chart") {
            ChartScreen(
                // Di sini Anda bisa menambahkan parameter navigasi kembali jika perlu
                // onBackClicked = { navController.popBackStack() }
            )
        }
        // composable(route = "profile") { ProfileScreen() }
    }
}