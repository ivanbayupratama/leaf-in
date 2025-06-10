// path: com/proyek/leaf_in/chart/ChartViewModel.kt

package com.proyek.leaf_in.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(ChartUiState())
    val uiState: StateFlow<ChartUiState> = _uiState.asStateFlow()

    init {
        loadCartItems()
    }

    private fun loadCartItems() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            val dummyItems = listOf(
                CartItem("1", "Bubur Ayam", 15000.0, "https://i.ibb.co/p3wYiD8/bubur-ayam.png", 1),
                CartItem("2", "Green Tea", 15000.0, "https://i.ibb.co/p3wYiD8/bubur-ayam.png", 1),
                CartItem("3", "Toast", 15000.0, "https://i.ibb.co/p3wYiD8/bubur-ayam.png", 1),
                CartItem("4", "Paleo Chicken Salad", 15000.0, "https://i.ibb.co/p3wYiD8/bubur-ayam.png", 1)
            )
            _uiState.update {
                it.copy(cartItems = dummyItems, isLoading = false)
            }
            recalculateTotals()
        }
    }

    fun increaseQuantity(itemId: String) {
        val updatedItems = _uiState.value.cartItems.map { item ->
            if (item.id == itemId) {
                item.copy(quantity = item.quantity + 1)
            } else {
                item
            }
        }
        _uiState.update { it.copy(cartItems = updatedItems) }
        recalculateTotals()
    }

    fun decreaseQuantity(itemId: String) {
        val updatedItems = _uiState.value.cartItems.mapNotNull { item ->
            if (item.id == itemId) {
                if (item.quantity > 1) {
                    item.copy(quantity = item.quantity - 1)
                } else {
                    null
                }
            } else {
                item
            }
        }
        _uiState.update { it.copy(cartItems = updatedItems) }
        recalculateTotals()
    }

    private fun recalculateTotals() {
        val currentItems = _uiState.value.cartItems
        val subTotal = currentItems.sumOf { it.price * it.quantity }
        val total = subTotal

        _uiState.update {
            it.copy(subTotal = subTotal, total = total)
        }
    }
}