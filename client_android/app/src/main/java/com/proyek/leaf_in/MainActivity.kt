package com.proyek.leaf_in

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.proyek.leaf_in.navigation.AppNavHost // <-- PASTIKAN IMPORT INI ADA
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
                    Screen.ProductList.route,
                    Screen.ProductDetails.route
                )

                val shouldShowBottomBar = bottomNavRoutes.any { navRoute ->
                    currentRoute?.startsWith(navRoute.removeSuffix("/{category}")) == true
                }

                Scaffold(
                    bottomBar = {
                        if (shouldShowBottomBar) {
                            Surface(shadowElevation = 8.dp) {
                                AppBottomNavigation(navController = navController)
                            }
                        }
                    }
                ) { innerPadding ->
                    // Memanggil AppNavHost dari file navigation/AppNavHost.kt
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

    NavigationBar(containerColor = Color(0xFF89D133)) {
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