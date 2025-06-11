package com.proyek.leaf_in.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.proyek.leaf_in.data.model.MenuItem // Import MenuItem dari model API Anda

@Entity(tableName = "menu_items")
data class MenuItemEntity(
    @PrimaryKey val id: String, // ID unik untuk setiap item, sesuaikan dengan ID dari API
    val name: String,
    val imageResName: String?, // Bisa null
    val price: Double,
    val category: String
    // Tambahkan properti lain yang relevan dari MenuItem Anda
) {
    // Fungsi konversi dari Entity Room ke Model API
    fun toMenuItem(): MenuItem {
        return MenuItem(
            id = this.id,
            name = this.name,
            imageResName = this.imageResName,
            price = this.price,
            category = this.category
        )
    }

    companion object {
        // Fungsi konversi dari Model API ke Entity Room
        fun fromMenuItem(menuItem: MenuItem): MenuItemEntity {
            return MenuItemEntity(
                id = menuItem.id,
                name = menuItem.name,
                imageResName = menuItem.imageResName,
                price = menuItem.price,
                category = menuItem.category
            )
        }
    }
}