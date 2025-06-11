package com.proyek.leaf_in.checkout

import androidx.annotation.DrawableRes

data class CartItem(
    val id: String,
    val name: String,
    val price: Double,
    var quantity: Int,
    @DrawableRes val imageRes: Int
)

data class CheckoutUiState(
    val cartItems: List<CartItem> = emptyList(),
    val deliveryAddress: String = "",
    val paymentMethod: String = "Leaf-in Pay",
    val subTotal: Double = 0.0,
    val deliveryFee: Double = 0.0,
    val total: Double = 0.0,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
