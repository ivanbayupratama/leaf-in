package com.proyek.leaf_in.chart

// Data class untuk satu item di dalam keranjang
data class CartItem(
    val id: String,
    val name: String,
    val price: Double,
    val imageUrl: String,
    val quantity: Int
)

// Data class untuk seluruh state dari layar Chart
data class ChartUiState(
    val cartItems: List<CartItem> = emptyList(),
    val subTotal: Double = 0.0,
    val total: Double = 0.0,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)