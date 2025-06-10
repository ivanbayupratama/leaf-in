package com.proyek.leaf_in.productdetails

import androidx.annotation.DrawableRes

data class Product(
    val id: Long,
    val name: String,
    val description: String,
    val price: Long,
    @DrawableRes val imageRes: Int?,
    @DrawableRes val logoIconRes: Int?,
    @DrawableRes val logoNameRes: Int?
)

data class ProductDetailsUiState(
    val product: Product? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)