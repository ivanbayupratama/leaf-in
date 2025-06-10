package com.proyek.leaf_in.productdetails

import androidx.lifecycle.SavedStateHandle // Import ini untuk SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.proyek.leaf_in.R
import com.proyek.leaf_in.data.repository.MenuRepository // Import MenuRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailsViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle, // <<< INJEKSI SAVEDSTATEHANDLE
    private val menuRepository: MenuRepository // <<< INJEKSI MENUREPOSITORY
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProductDetailsUiState())
    val uiState: StateFlow<ProductDetailsUiState> = _uiState.asStateFlow()

    init {
        // Ambil productId dari SavedStateHandle saat ViewModel diinisialisasi
        val productId: String? = savedStateHandle["productId"] // Mengambil productId dari navigasi
        if (productId != null) {
            loadProductDetails(productId) // Memuat detail produk berdasarkan ID
        } else {
            _uiState.update { it.copy(errorMessage = "Product ID tidak ditemukan.") }
        }
    }

    // Fungsi untuk memuat detail produk (akan dipanggil dari init atau LaunchedEffect di UI)
    fun loadProductDetails(productId: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            try {
                // TODO: Ganti ini dengan panggilan ke Repository yang sebenarnya (jika ada API getProductById).
                // Saat ini, kita simulasi dengan data mock dan pastikan ID-nya STRING.

                delay(1000) // Simulasi loading

                val mockProduct = when (productId) {
                    "1" -> Product( // <<< ID SEBAGAI STRING
                        id = "1",
                        name = "Bubur Ayam (Test)",
                        description = "Warm rice porridge topped with shredded chicken, fried shallots, soy sauce, and crispy crackers. Light, tasty, and comforting – perfect any time of day.",
                        price = 15000L,
                        imageRes = R.drawable.bubur_ayam, // Contoh resource ID
                        logoIconRes = R.drawable.logotanpanama, // Contoh resource ID
                        logoNameRes = R.drawable.nama // Contoh resource ID
                    )
                    "2" -> Product( // <<< ID SEBAGAI STRING
                        id = "2",
                        name = "Toast (Test)",
                        description = "Crispy toast with butter and jam, a classic breakfast delight.",
                        price = 25000L,
                        imageRes = R.drawable.toast, // Contoh resource ID
                        logoIconRes = R.drawable.logotanpanama,
                        logoNameRes = R.drawable.nama
                    )
                    // Tambahkan mock produk lain sesuai kebutuhan
                    else -> null
                }

                if (mockProduct != null) {
                    _uiState.update { it.copy(isLoading = false, product = mockProduct) }
                } else {
                    _uiState.update { it.copy(isLoading = false, errorMessage = "Produk tidak ditemukan.") }
                }

            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, errorMessage = "Gagal memuat produk: ${e.message}") }
            }
        }
    }

    // Fungsi yang dipanggil saat tombol "Add to Cart" diklik
    fun onAddToCartClicked() {
        // TODO: Implementasi logika menambahkan produk ke keranjang (misalnya, panggil CartRepository)
        viewModelScope.launch {
            _uiState.update { it.copy(isAddedToCart = true) } // Set state added to cart
            delay(1500) // Tampilkan state "added to cart" sebentar
            _uiState.update { it.copy(isAddedToCart = false) } // Reset state
        }
    }

    // Fungsi untuk memberitahu ViewModel bahwa error sudah ditampilkan di UI
    fun errorShown() {
        _uiState.update { it.copy(errorMessage = null) }
    }
}