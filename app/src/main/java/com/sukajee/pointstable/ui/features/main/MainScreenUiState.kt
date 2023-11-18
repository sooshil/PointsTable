package com.sukajee.pointstable.ui.features.main

import com.sukajee.pointstable.data.model.Series

data class MainScreenUiState(
    val isLoading: Boolean = true,
    val isError: Boolean = false,
    val series: List<Series> = emptyList()
)
