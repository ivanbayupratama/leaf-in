package com.proyek.leaf_in.di

import android.content.Context
import androidx.room.Room
import com.proyek.leaf_in.data.local.AppDatabase // Pastikan ini diimpor
import com.proyek.leaf_in.data.local.dao.MenuItemDao // Pastikan ini diimpor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "leaf_in_database" // Nama database Anda
        ).build()
    }

    @Provides
    @Singleton
    fun provideMenuItemDao(database: AppDatabase): MenuItemDao {
        return database.menuItemDao()
    }
}