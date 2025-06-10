package com.proyek.leaf_in.di

import com.proyek.leaf_in.data.remote.api.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    // Fungsi ini memberitahu Hilt cara membuat objek Retrofit
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        // Ini untuk melihat log request & response di Logcat, sangat membantu saat debugging
        val loggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()

        // PENTING: 10.0.2.2 adalah alamat khusus dari emulator Android
        // untuk mengakses localhost di komputer/laptop.
        return Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080/") // Sesuaikan port jika Tim Backend pakai port lain
            .addConverterFactory(GsonConverterFactory.create())
            .client(client) // Tambahkan client dengan logging interceptor
            .build()
    }

    // memberitau Hilt cara membuat ApiService, dengan menggunakan Retrofit di atas
    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }
}