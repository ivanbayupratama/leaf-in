// path: com/proyek/leaf_in/beverages/BeverageUiState.kt

package com.proyek.leaf_in.beverages

import com.proyek.leaf_in.data.model.MenuItem

data class BeverageUiState(
    val beverages: List<MenuItem> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)