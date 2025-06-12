package com.proyek.leaf_in.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.proyek.leaf_in.data.model.MenuItem
// Hapus import yang tidak perlu seperti MenuRepository dan IoDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    // Kita tidak lagi butuh MenuRepository, jadi Hilt tidak perlu menyuntikkannya
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        // Panggil fungsi untuk memuat data dummy
        loadDummyProducts()
    }

    /**
     * Fungsi ini sekarang hanya bertugas untuk membuat daftar produk statis
     * dan memperbarui UI state.
     */
    private fun loadDummyProducts() {
        // Set isLoading true agar UI menampilkan spinner sebentar (efek loading)
        _uiState.update { it.copy(isLoading = true) }

        // Data dummy untuk Meals
        val dummyMeals = listOf(
            MenuItem(
                id = "1",
                name = "Toast",
                category = "Meals",
                price = 25000.0,
                imageResName = "toast" // Pastikan ada file 'toast.png' di res/drawable
            ),
            MenuItem(
                id = "2",
                name = "Bubur Ayam",
                category = "Meals",
                price = 15000.0,
                imageResName = "bubur_ayam" // Pastikan ada file 'bubur_ayam.png'
            ),
            MenuItem(
                id = "3",
                name = "Salad",
                category = "Meals",
                price = 40000.0,
                imageResName = "salad" // Pastikan ada file 'salad.png'
            )
        )

        // Data dummy untuk Beverages
        val dummyBeverages = listOf(
            MenuItem(
                id = "4",
                name = "Smoothies",
                category = "Beverages",
                price = 25000.0,
                imageResName = "smoothies" // Pastikan ada file 'smoothies.png'
            ),
            MenuItem(
                id = "5",
                name = "Jamu Beras Kencur",
                category = "Beverages",
                price = 8000.0,
                imageResName = "jamu_beras_kencur" // Pastikan ada file 'jamu_beras_kencur.png'
            ),
            MenuItem(
                id = "6",
                name = "Green Tea",
                category = "Beverages",
                price = 12000.0,
                imageResName = "green_tea" // Pastikan ada file 'green_tea.png'
            )
        )

        // Update state dengan data dummy setelah "loading"
        viewModelScope.launch {
            // Kita bisa beri sedikit delay untuk mensimulasikan proses loading
            // import kotlinx.coroutines.delay
            // delay(500)

            _uiState.update {
                it.copy(
                    isLoading = false,
                    meals = dummyMeals,
                    beverages = dummyBeverages
                )
            }
        }
    }
}