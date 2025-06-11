package com.proyek.leaf_in.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.proyek.leaf_in.data.repository.MenuRepository
import com.proyek.leaf_in.di.IoDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest // <<< Pastikan import ini ada
import kotlinx.coroutines.flow.update // <<< Pastikan import ini ada
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.proyek.leaf_in.data.model.MenuItem // Pastikan MenuItem diimpor
// import com.proyek.leaf_in.home.HomeUiState // Pastikan HomeUiState diimpor

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val menuRepository: MenuRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        // Ini adalah bagian yang mengumpulkan Flow dari Repository
        // dan memperbarui UI State setiap kali ada data baru dari Room.
        observeLocalProducts()
        refreshHomeData()
        viewModelScope.launch(ioDispatcher) {
            menuRepository.getAllProducts().collectLatest { menuItems -> // <<< Kunci perbaikan ada di sini
                _uiState.update { currentState ->
                    // Di sini, 'menuItems' sudah bertipe List<MenuItem>
                    val meals = menuItems.filter { it.category == "Meals" }
                    val beverages = menuItems.filter { it.category == "Beverages" }
                    currentState.copy(
                        isLoading = false, // isLoading akan true saat refreshProducts, lalu false di sini
                        meals = meals,
                        beverages = beverages,
                        errorMessage = null // Bersihkan error jika data berhasil dimuat
                    )
                }
            }
        }

        // Panggil refresh untuk mengambil data terbaru dari API saat ViewModel pertama kali dibuat.
        refreshHomeData()
    }
    private fun observeLocalProducts() {
        viewModelScope.launch(ioDispatcher) {
            // Flow ini akan terus berjalan dan otomatis memperbarui UI setiap kali
            // data di dalam Room (database) berubah.
            menuRepository.getAllProducts().collect { menuItems ->
                _uiState.update { currentState ->
                    // Filter data berdasarkan kategori yang diterima dari API
                    val meals = menuItems.filter { it.category.equals("Meals", ignoreCase = true) }
                    val beverages = menuItems.filter { it.category.equals("Beverages", ignoreCase = true) }

                    currentState.copy(
                        // isLoading diatur ke false di sini karena kita sudah menerima data (baik lama maupun baru)
                        isLoading = false,
                        meals = meals,
                        beverages = beverages
                    )
                }
            }
        }
    }

    // Fungsi untuk memuat/refresh data Home dari API
    fun refreshHomeData() {
        viewModelScope.launch(ioDispatcher) {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) } // Set isLoading true saat memulai refresh
            try {
                menuRepository.refreshProducts() // Memanggil API dan menyimpan ke Room
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false, // Set isLoading false saat ada error
                        errorMessage = "Gagal memuat menu dari API: ${e.message}"
                    )
                }
            }
        }
    }

    // Fungsi yang akan dipanggil dari UI untuk menampilkan Toast error
    fun errorShown() {
        _uiState.update { it.copy(errorMessage = null) }
    }

    // Fungsi-fungsi lain untuk interaksi pengguna (misalnya klik Order) bisa ditambahkan di sini
}