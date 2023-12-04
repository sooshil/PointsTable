package com.sukajee.pointstable.ui.features.addseries

import com.sukajee.pointstable.data.model.Series

data class AddEditSeriesUiState(
    val isLoading: Boolean = true,
    val isEditModeActive: Boolean = false,
    val seriesBeingEdited: Series? = null,
    val isError: Boolean = false,
)
