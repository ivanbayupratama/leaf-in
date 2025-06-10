package com.proyek.leaf_in.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import com.proyek.leaf_in.data.local.entities.MenuItemEntity // Import entitas Anda

@Dao
interface MenuItemDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMenuItems(items: List<MenuItemEntity>)

    @Query("SELECT * FROM menu_items")
    fun getAllMenuItems(): Flow<List<MenuItemEntity>> // Menggunakan Flow untuk Observability

    @Query("DELETE FROM menu_items")
    suspend fun deleteAllMenuItems()
}