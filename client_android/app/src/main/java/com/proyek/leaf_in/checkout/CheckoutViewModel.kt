package com.proyek.leaf_in.checkout

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.proyek.leaf_in.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CheckoutViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(CheckoutUiState())
    val uiState: StateFlow<CheckoutUiState> = _uiState.asStateFlow()

    init {
        loadCheckoutData()
    }

    private fun loadCheckoutData() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            // Simulasi mengambil data dari keranjang (Cart)
            val dummyItems = listOf(
                CartItem("1", "Bubur Ayam", 15000.0, 1, R.drawable.bubur_ayam),
                CartItem("2", "Green Tea", 15000.0, 1, R.drawable.green_tea), // Ganti dengan drawable yang benar
                CartItem("3", "Toast", 15000.0, 1, R.drawable.toast) // Ganti dengan drawable yang benar
            )

            val subTotal = dummyItems.sumOf { it.price * it.quantity }
            val deliveryFee = 5000.0 // Contoh biaya pengiriman
            val total = subTotal + deliveryFee

            _uiState.update {
                it.copy(
                    isLoading = false,
                    cartItems = dummyItems,
                    deliveryAddress = "Jl. Margonda Raya No. 100, Depok",
                    subTotal = subTotal,
                    deliveryFee = deliveryFee,
                    total = total
                )
            }
        }
    }

    fun placeOrder() {
        // TODO: Tambahkan logika untuk mengirim pesanan ke backend
        println("Pesanan berhasil dibuat!")
    }
}
