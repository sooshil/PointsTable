package com.sukajee.pointstable.ui.features.table

sealed class PointsTableUiEvents {
    data class GetTableData(val seriesId: Int): PointsTableUiEvents()
}