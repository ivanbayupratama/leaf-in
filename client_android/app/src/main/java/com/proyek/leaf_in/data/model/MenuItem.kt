package com.proyek.leaf_in.data.model

import kotlinx.serialization.Serializable


@Serializable
data class MenuItem(
    val id: String,
    val name: String,
    val category: String,
    val price: Double,
    val imageResName: String?
)