package com.proyek.leaf_in.productdetails

import androidx.annotation.DrawableRes

/**
 * Data class yang merepresentasikan detail lengkap sebuah produk untuk ditampilkan di UI.
 * Menggunakan @DrawableRes untuk referensi gambar lokal.
 */
data class Product(
    val id: String,
    val name: String,
    val description: String,
    val price: Long,
    @DrawableRes val imageRes: Int?,
    @DrawableRes val logoIconRes: Int?,
    @DrawableRes val logoNameRes: Int?
)