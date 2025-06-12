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
import com.proyek.leaf_in.beverages.BeverageScreen
import com.proyek.leaf_in.cart.CartScreen
import com.proyek.leaf_in.checkout.CheckoutScreen
import com.proyek.leaf_in.home.HomeScreen
import com.proyek.leaf_in.main.profile.EditProfileScreen // <-- IMPORT HALAMAN PROFIL
import com.proyek.leaf_in.meals.MealsScreen
import com.proyek.leaf_in.productdetails.ProductDetailsScreen

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
            when (val category = backStackEntry.arguments?.getString("category")) {
                "meals" -> MealsScreen(navController = navController)
                "beverages" -> BeverageScreen(navController = navController)
                else -> Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "Kategori tidak dikenal: $category")
                }
            }
        }

        composable(Screen.Cart.route) {
            CartScreen(
                onBackClicked = { navController.popBackStack() },
                onCheckoutClicked = { navController.navigate(Screen.Checkout.route) }
            )
        }

        // --- INI IMPLEMENTASI YANG BENAR UNTUK RUTE PROFILE ---
        composable(Screen.Profile.route) {
            EditProfileScreen(
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        composable(Screen.ProductDetails.route) {
            ProductDetailsScreen(
                // onBackClick = { navController.popBackStack() }
            )
        }

        composable(Screen.Checkout.route) {
            CheckoutScreen(
                onBackClicked = { navController.popBackStack() },
                onOrderPlaced = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Home.route) { inclusive = true }
                    }
                }
            )
        }
    }
}