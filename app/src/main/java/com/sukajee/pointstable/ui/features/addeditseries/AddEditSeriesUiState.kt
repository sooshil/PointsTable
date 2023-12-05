package com.sukajee.pointstable.ui.features.addeditseries

import com.sukajee.pointstable.data.model.Series

data class AddEditSeriesUiState(
    val isLoading: Boolean = true,
    val isEditModeActive: Boolean = false,
    val seriesId: Int? = null,
    val seriesName: String? = "",
    val roundRobinCount: String? = "",
    val teamNames: List<String> = emptyList(),
    val isError: Boolean = false,
)
