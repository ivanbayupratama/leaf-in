package com.proyek.leaf_in.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.proyek.leaf_in.data.local.dao.MenuItemDao // Pastikan ini diimpor
import com.proyek.leaf_in.data.local.entities.MenuItemEntity // Pastikan ini diimpor

@Database(entities = [MenuItemEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun menuItemDao(): MenuItemDao
}