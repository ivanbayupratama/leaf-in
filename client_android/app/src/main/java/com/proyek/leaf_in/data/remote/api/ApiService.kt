package com.proyek.leaf_in.data.remote.api

import com.proyek.leaf_in.data.model.LoginRequest
import com.proyek.leaf_in.data.model.RegisterRequest
import com.proyek.leaf_in.data.model.AuthResponse
import com.proyek.leaf_in.data.model.CreateOrderRequest
import com.proyek.leaf_in.data.model.GenericResponse
import com.proyek.leaf_in.data.model.MenuItem // Import MenuItem juga
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    @POST("register")
    suspend fun registerUser(@Body request: RegisterRequest): AuthResponse

    @POST("login")
    suspend fun loginUser(@Body request: LoginRequest): AuthResponse

    @GET("products")
    suspend fun getAllProducts(): List<MenuItem> // Kita tambahkan juga untuk produk
    @POST("checkout")
    suspend fun checkout(@Body request: CreateOrderRequest): GenericResponse
}