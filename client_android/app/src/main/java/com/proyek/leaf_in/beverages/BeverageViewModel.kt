// path: com/proyek/leaf_in/beverages/BeverageViewModel.kt

package com.proyek.leaf_in.beverages

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.proyek.leaf_in.data.model.MenuItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BeverageViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(BeverageUiState())
    val uiState: StateFlow<BeverageUiState> = _uiState.asStateFlow()

    init {
        loadAllBeverages()
    }

    private fun loadAllBeverages() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            // Simulasi mengambil data dari internet/database
            val allBeverages = listOf(
                MenuItem("10", "Smoothies", "https://i.ibb.co/h2Dyr2F/smoothies.jpg", 25000.0, "Beverages"),
                MenuItem("11", "Jamu Beras Kencur", "https://i.ibb.co/RggGg55/jamu.jpg", 8000.0, "Beverages"),
                MenuItem("12", "Green Tea", "https://i.ibb.co/fC0J3kK/green-tea.jpg", 12000.0, "Beverages"),
                MenuItem("13", "Kopi Susu", "https://i.ibb.co/LQrG5jN/kopi-susu.jpg", 18000.0, "Beverages"),
                MenuItem("14", "Es Teh Manis", "https://i.ibb.co/BfL3xY4/es-teh.jpg", 5000.0, "Beverages"),
                MenuItem("15", "Jus Alpukat", "https://i.ibb.co/zVPgNws/jus-alpukat.jpg", 15000.0, "Beverages"),
                MenuItem("16", "Coklat Panas", "https://i.ibb.co/Jqj846R/coklat-panas.jpg", 20000.0, "Beverages"),
                MenuItem("17", "Wedang Jahe", "https://i.ibb.co/fG2bC3J/wedang-jahe.jpg", 10000.0, "Beverages")
            )

            _uiState.update {
                it.copy(isLoading = false, beverages = allBeverages)
            }
        }
    }
}