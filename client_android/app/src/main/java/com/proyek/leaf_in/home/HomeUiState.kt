package com.proyek.leaf_in.home

import com.proyek.leaf_in.data.model.MenuItem

data class HomeUiState(
    val meals: List<MenuItem> = emptyList(),
    val beverages: List<MenuItem> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)