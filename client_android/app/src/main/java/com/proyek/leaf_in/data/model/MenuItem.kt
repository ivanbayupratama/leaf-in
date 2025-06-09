package com.proyek.leaf_in.data.model

data class MenuItem(
    val id: String,
    val name: String,
    val imageUrl: String,
    val price: Double,
    val category: String // "Meals" atau "Beverages"
)