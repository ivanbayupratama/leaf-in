package com.proyek.leaf_in.data.model

import kotlinx.serialization.Serializable

@Serializable
data class CreateOrderRequest(
    val userEmail: String,
    val items: List<OrderItemRequest>
)

@Serializable
data class OrderItemRequest(
    val productId: String,
    val quantity: Int
)

@Serializable
data class GenericResponse(
    val message: String
)