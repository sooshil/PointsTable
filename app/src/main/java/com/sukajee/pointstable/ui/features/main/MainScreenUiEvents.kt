package com.sukajee.pointstable.ui.features.main

import com.sukajee.pointstable.data.model.Series

sealed class MainScreenUiEvents {
    data class OnCreateUpdateSeriesClick(val series: Series, val isUpdate: Boolean): MainScreenUiEvents()
    data class OnDeleteSeries(val series: Series): MainScreenUiEvents()
    data object OnUndoDeleteClick: MainScreenUiEvents()
    data object OnDeleteSeriesIgnored: MainScreenUiEvents()
}