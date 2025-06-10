// path: com/proyek/leaf_in/meals/MealsUiState.kt

package com.proyek.leaf_in.meals

import com.proyek.leaf_in.data.model.MenuItem

data class MealsUiState(
    val meals: List<MenuItem> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)