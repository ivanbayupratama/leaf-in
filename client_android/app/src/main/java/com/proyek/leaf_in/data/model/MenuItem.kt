package com.proyek.leaf_in.data.model

import android.content.Context
import androidx.annotation.DrawableRes
import kotlinx.serialization.Serializable

@Serializable
data class MenuItem(
    val id: String,
    val name: String,
    val category: String,
    val price: Double,
    val imageResName: String? // <-- Menerima nama resource dari API
) {
    @DrawableRes
    fun getDrawableResourceId(context: Context): Int {
        if (imageResName.isNullOrBlank()) return 0
        return context.resources.getIdentifier(
            imageResName,
            "drawable",
            context.packageName
        )
    }
}