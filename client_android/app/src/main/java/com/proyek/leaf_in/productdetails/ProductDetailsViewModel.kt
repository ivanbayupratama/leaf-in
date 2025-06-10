package com.proyek.leaf_in.productdetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.proyek.leaf_in.R
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class ProductDetailsViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(ProductDetailsUiState())
    val uiState: StateFlow<ProductDetailsUiState> = _uiState.asStateFlow()

    init {
        fetchProductDetails()
    }

    private fun fetchProductDetails() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            // UNTUK SEMENTARA, GUNAKAN NULL PADA SEMUA RESOURCE GAMBAR
            val dummyProduct = Product(
                id = 1L,
                name = "Bubur Ayam (Test)",
                description = "Ini adalah deskripsi untuk mengetes apakah aplikasi masih crash.",
                price = 15000L,
                imageRes = null,
                logoIconRes = null,
                logoNameRes = null
            )

            _uiState.update {
                it.copy(
                    isLoading = false,
                    product = dummyProduct
                )
            }
        }
    }

    fun onAddToCartClicked() {
        println("Product added to cart: ${uiState.value.product?.name}")
    }
}