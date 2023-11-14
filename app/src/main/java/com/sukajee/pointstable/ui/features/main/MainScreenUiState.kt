package com.sukajee.pointstable.ui.features.main

import com.sukajee.pointstable.data.model.Match

data class MainScreenUiState(
    val isLoading: Boolean = true,
    val isError: Boolean = false,
    val matches: List<Match> = emptyList()
)
