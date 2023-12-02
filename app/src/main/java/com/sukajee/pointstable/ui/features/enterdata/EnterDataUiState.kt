package com.sukajee.pointstable.ui.features.enterdata

import com.sukajee.pointstable.data.model.Series

data class EnterDataUiState(
    val isLoading: Boolean = true,
    val isError: Boolean = false,
    val series: Series? = null
)