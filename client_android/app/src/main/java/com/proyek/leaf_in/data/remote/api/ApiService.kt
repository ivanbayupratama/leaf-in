package com.proyek.leaf_in.data.remote.api

import com.proyek.leaf_in.data.model.AuthRequest
import com.proyek.leaf_in.data.model.AuthResponse
import com.proyek.leaf_in.data.model.MenuItem // Import MenuItem juga
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    @POST("register")
    suspend fun registerUser(@Body request: AuthRequest): AuthResponse

    @POST("login")
    suspend fun loginUser(@Body request: AuthRequest): AuthResponse

    @GET("products")
    suspend fun getAllProducts(): List<MenuItem> // Kita tambahkan juga untuk produk
}