package com.proyek.leaf_in.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.proyek.leaf_in.data.repository.MenuRepository // Akan dibuat di langkah selanjutnya
import com.proyek.leaf_in.di.IoDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val menuRepository: MenuRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        loadHomeData()
    }

    private fun loadHomeData() {
        viewModelScope.launch(ioDispatcher) {
            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)
            try {
                val menuItems = menuRepository.getAllMenuItems()
                val meals = menuItems.filter { it.category == "Meals" }
                val beverages = menuItems.filter { it.category == "Beverages" }
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    meals = meals,
                    beverages = beverages
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = "Failed to load menu: ${e.message}"
                )
                // Log error or handle it further
            }
        }
    }

    // Fungsi-fungsi lain untuk interaksi pengguna (misalnya klik Order) bisa ditambahkan di sini
}