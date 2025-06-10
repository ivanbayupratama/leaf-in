package com.proyek.leaf_in

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
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
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.proyek.leaf_in.auth.login.LoginScreen
import com.proyek.leaf_in.auth.register.RegisterScreen
import com.proyek.leaf_in.beverages.BeverageScreen // <-- 1. IMPORT LAYAR BARU
import com.proyek.leaf_in.cart.CartScreen
import com.proyek.leaf_in.home.HomeScreen
import com.proyek.leaf_in.meals.MealsScreen
import com.proyek.leaf_in.navigation.Screen
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

                val bottomNavRoutes = setOf(
                    Screen.Home.route,
                    Screen.Cart.route,
                    Screen.Profile.route,
                    Screen.ProductList.route
                )
                val shouldShowBottomBar = bottomNavRoutes.any { currentRoute?.startsWith(it.removeSuffix("/{category}")) == true }


                Scaffold(
                    bottomBar = {
                        if (shouldShowBottomBar) {
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

    NavigationBar(
        containerColor = Color(0xFF89D133)
    ) {
        val bottomNavItems = listOf(
            Triple(Screen.Home, R.drawable.home_button, "Home"),
            Triple(Screen.Cart, R.drawable.cart_button, "Cart"),
            Triple(Screen.Profile, R.drawable.profile, "Profile")
        )

        bottomNavItems.forEach { (screen, iconId, label) ->
            NavigationBarItem(
                selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    Icon(
                        painter = painterResource(id = iconId),
                        contentDescription = label,
                        modifier = Modifier.size(24.dp)
                    )
                },
                label = { Text(label, color = Color.Black) },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = Color.Transparent,
                    selectedIconColor = Color.Black,
                    unselectedIconColor = Color.Black.copy(alpha = 0.6f)
                )
            )
        }
    }
}

@Composable
fun AppNavHost(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(
        navController = navController,
        startDestination = Screen.Login.route,
        modifier = modifier
    ) {
        composable(Screen.Login.route) {
            LoginScreen(
                onLoginSuccess = { navController.navigate(Screen.Home.route) { popUpTo(Screen.Login.route) { inclusive = true } } },
                onNavigateToRegister = { navController.navigate(Screen.Register.route) }
            )
        }

        composable(Screen.Register.route) {
            RegisterScreen(
                onRegistrationSuccess = { navController.popBackStack() },
                onNavigateBackToLogin = { navController.popBackStack() }
            )
        }

        composable(Screen.Home.route) {
            HomeScreen(navController = navController)
        }

        composable(
            route = Screen.ProductList.route,
            arguments = listOf(navArgument("category") { type = NavType.StringType })
        ) { backStackEntry ->
            val category = backStackEntry.arguments?.getString("category")

            // --- 2. PERBARUI BAGIAN INI ---
            when (category) {
                "meals" -> MealsScreen(navController = navController)
                "beverages" -> BeverageScreen(navController = navController)
            }
        }

        composable(Screen.Cart.route) {
            CartScreen(
                onBackClicked = { navController.popBackStack() },
                onCheckoutClicked = {
                    // TODO: Navigasi ke halaman pembayaran nanti
                }
            )
        }

        composable(Screen.Profile.route) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = "Halaman Profil")
            }
        }
    }
}