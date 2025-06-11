package com.proyek.leaf_in.server.services

import com.proyek.leaf_in.server.data.DatabaseFactory.dbQuery
import com.proyek.leaf_in.server.data.model.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.inList
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.LocalDateTime
import java.util.UUID

class OrderService {

    suspend fun createOrder(request: CreateOrderRequest): GenericResponse {

        val userId = dbQuery {
            Users.select { Users.email eq request.userEmail }.singleOrNull()?.get(Users.id)
        } ?: return GenericResponse("User with email ${request.userEmail} not found")

        val productIds = request.items.map { it.productId }

        val productsInDb = dbQuery {
            Products.select { Products.id inList productIds }.map {
                it[Products.id] to it[Products.price]
            }.toMap()
        }

        var totalPrice = 0
        for (item in request.items) {
            val price = productsInDb[item.productId] ?: continue
            totalPrice += price * item.quantity
        }

        val newOrderId = "order-${UUID.randomUUID()}"

        transaction {
            Orders.insert {
                it[id] = newOrderId
                it[this.userId] = userId
                it[this.totalPrice] = totalPrice
                it[orderDate] = LocalDateTime.now()
            }

            for (item in request.items) {
                val price = productsInDb[item.productId] ?: continue
                OrderItems.insert {
                    it[id] = "item-${UUID.randomUUID()}"
                    it[orderId] = newOrderId
                    it[productId] = item.productId
                    it[quantity] = item.quantity
                    it[pricePerItem] = price
                }
            }
        }

        return GenericResponse("Order successfully confirmed")
    }
}