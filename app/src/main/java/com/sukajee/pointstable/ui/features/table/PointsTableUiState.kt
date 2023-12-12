package com.sukajee.pointstable.ui.features.table

import com.sukajee.pointstable.data.model.PointTableRow
import com.sukajee.pointstable.data.model.Series

data class PointsTableUiState(
    val isLoading: Boolean = true,
    val currentSeriesName: String = "",
    val isMatchDataEmpty: Boolean = true,
    val seriesList: List<Series> = emptyList(),
    val pointTableRows: List<PointTableRow> = emptyList()
)