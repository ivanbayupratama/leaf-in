// path: com/proyek/leaf_in/MainActivity.kt

package com.proyek.leaf_in

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.proyek.leaf_in.auth.login.LoginScreen
import com.proyek.leaf_in.auth.register.RegisterScreen

import com.proyek.leaf_in.home.HomeScreen
import com.proyek.leaf_in.navigation.Screen

import com.proyek.leaf_in.chart.ChartScreen // <-- 1. IMPORT ChartScreen
import com.proyek.leaf_in.home.HomeScreen
import com.proyek.leaf_in.ui.theme.Leaf_inTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Leaf_inTheme {
                val navController = rememberNavController()

                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route

                // Daftar halaman yang akan menampilkan navigasi bawah
                val bottomNavRoutes = setOf(
                    Screen.Home.route,
                    Screen.Cart.route,
                    Screen.Profile.route
                )

                val shouldShowBottomBar = currentRoute in bottomNavRoutes

                Scaffold(
                    bottomBar = {
                        if (shouldShowBottomBar) {
                            // Menambahkan Surface untuk efek shadow
                            Surface(shadowElevation = 8.dp) {
                                AppBottomNavigation(navController = navController)
                            }
                        }
                    }
                ) { innerPadding ->
                    AppNavHost(
                        navController = navController,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun AppBottomNavigation(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    Surface(shadowElevation = 100.dp) {
        NavigationBar(
            containerColor = Color(0xFF89D133) // Warna hijau lemon
        ) {
            val bottomNavItems = listOf(Screen.Home, Screen.Cart, Screen.Profile)
            val icons = listOf(R.drawable.home_button, R.drawable.cart_button, R.drawable.profile)
            val labels = listOf("Home", "Cart", "Profile")

            bottomNavItems.forEachIndexed { index, screen ->
                NavigationBarItem(
                    // Logika untuk mengetahui item mana yang sedang aktif/terpilih
                    selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                    onClick = {
                        navController.navigate(screen.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    icon = {
                        Icon(
                            painter = painterResource(id = icons[index]),
                            contentDescription = labels[index],
                            modifier = Modifier.size(30.dp)
                        )
                    },
                    label = { Text(labels[index], color = Color.Black) },
                    colors = NavigationBarItemDefaults.colors(
                        indicatorColor = Color.Transparent,
                        selectedIconColor = Color.Black,
                        unselectedIconColor = Color.Black.copy(alpha = 0.6f) // Ikon tidak aktif sedikit transparan
                    )
                )
            }
        }
    }
}

@Composable
fun AppNavHost(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(
        navController = navController,

        startDestination = Screen.Login.route,
        modifier = modifier
=======
        startDestination = "login" // Atau "home" jika mau langsung ke home untuk testing

    ) {
        composable(Screen.Login.route) { LoginScreen(
            onLoginSuccess = { navController.navigate(Screen.Home.route) { popUpTo(Screen.Login.route) { inclusive = true } } },
            onNavigateToRegister = { navController.navigate(Screen.Register.route) }
        ) }

        composable(Screen.Register.route) { RegisterScreen(
            onRegistrationSuccess = { navController.popBackStack() },
            onNavigateBackToLogin = { navController.popBackStack() }
        ) }

        composable(Screen.Home.route) { HomeScreen(navController = navController) }

        composable(
            route = Screen.ProductList.route,
            arguments = listOf(navArgument("category") { type = NavType.StringType })
        ) {
            // Nanti di sini akan diisi oleh temanmu
            // Untuk sementara, kita bisa tampilkan teks placeholder
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = "Halaman untuk kategori: ${it.arguments?.getString("category")}")
            }
        }


        // Placeholder untuk halaman Cart dan Profile
        composable(Screen.Cart.route) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = "Halaman Keranjang (Cart)")
            }
        }
        composable(Screen.Profile.route) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = "Halaman Profil")
            }
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