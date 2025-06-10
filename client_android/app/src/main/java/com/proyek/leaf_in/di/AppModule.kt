package com.proyek.leaf_in.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton
import com.proyek.leaf_in.di.IoDispatcher // <<< PASTIKAN IMPORT INI ADA

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @IoDispatcher // <<< TAMBAHKAN ANOTASI INI DI SINI
    @Provides
    @Singleton // Opsional: Tambahkan @Singleton untuk provideIoDispatcher jika Anda ingin instance tunggal
    fun provideIoDispatcher(): CoroutineDispatcher = Dispatchers.IO
}