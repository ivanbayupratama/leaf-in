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
import com.proyek.leaf_in.home.HomeScreen // Pastikan ini diimpor
import com.proyek.leaf_in.ui.theme.Leaf_inTheme
import dagger.hilt.android.AndroidEntryPoint // Ini penting untuk Hilt

// Anotasi @AndroidEntryPoint pada MainActivity agar Hilt dapat mengelola dependensi
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
        startDestination = "login"
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
                onRegistrationSuccess = {
                    navController.popBackStack()
                },
                onNavigateBackToLogin = {
                    navController.popBackStack()
                }
            )
        }
        composable(route = "home") {
            HomeScreen()
        }

        // TODO:  fitur buat menambahkan rute untuk "cart" dan "profile" next
        // composable(route = "cart") { CartScreen() }
        // composable(route = "profile") { ProfileScreen() }
    }
}