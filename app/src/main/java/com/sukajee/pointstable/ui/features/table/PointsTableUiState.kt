package com.sukajee.pointstable.ui.features.table

import com.sukajee.pointstable.data.model.PointTableRow

data class PointsTableUiState(
    val isLoading: Boolean = true,
    val isError: Boolean = false,
    val pointTableRows: List<PointTableRow> = emptyList()
)