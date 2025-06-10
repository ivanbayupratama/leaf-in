package com.proyek.leaf_in.productdetails

/**
 * Data class yang merepresentasikan semua state untuk Product Details Screen.
 */
data class ProductDetailsUiState(
    val product: Product? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isAddedToCart: Boolean = false
)