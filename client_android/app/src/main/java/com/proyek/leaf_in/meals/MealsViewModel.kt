// path: com/proyek/leaf_in/meals/MealsViewModel.kt

package com.proyek.leaf_in.meals

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
class MealsViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(MealsUiState())
    val uiState: StateFlow<MealsUiState> = _uiState.asStateFlow()

    init {
        loadAllMeals()
    }

    private fun loadAllMeals() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            // Simulasi mengambil data dari internet/database
            // Di sini kita tambahkan lebih banyak item untuk menunjukkan fungsi scroll
            val allMeals = listOf(
                MenuItem("1", "Toast", "https://i.ibb.co/3mPZ2Yn/toast.jpg", 25000.0, "Meals"),
                MenuItem("2", "Bubur Ayam", "https://i.ibb.co/p3wYiD8/bubur-ayam.png", 15000.0, "Meals"),
                MenuItem("3", "Salad", "https://i.ibb.co/6y4jY45/salad.jpg", 40000.0, "Meals"),
                MenuItem("4", "Kentang Rebus", "https://i.ibb.co/mHq3gTh/kentang-rebus.jpg", 18000.0, "Meals"),
                MenuItem("5", "Nasi Bakar", "https://i.ibb.co/YyvRkYv/nasi-bakar.jpg", 27000.0, "Meals"),
                MenuItem("6", "Paleo Chicken Salad", "https://i.ibb.co/z5pD5gt/paleo-chicken-salad.jpg", 45000.0, "Meals"),
                MenuItem("7", "Oat Meal", "https://i.ibb.co/6gGzYfQ/oatmeal.jpg", 22000.0, "Meals"),
                MenuItem("8", "Pancake", "https://i.ibb.co/j3qfV0s/pancake.jpg", 28000.0, "Meals")
            )

            _uiState.update {
                it.copy(isLoading = false, meals = allMeals)
            }
        }
    }
}