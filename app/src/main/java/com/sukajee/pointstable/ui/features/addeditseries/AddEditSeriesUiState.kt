package com.sukajee.pointstable.ui.features.addeditseries

data class AddEditSeriesUiState(
    val isLoading: Boolean = true,
    val isEditModeActive: Boolean = false,
    val seriesId: Int = -1,
    val seriesName: String = "",
    val roundRobinCount: String = "",
    val teamNames: List<String> = emptyList(),
    val isError: Boolean = false,
)
