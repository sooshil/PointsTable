package com.sukajee.pointstable.ui.features.addeditseries

sealed class AddEditSeriesScreenUiEvents {
    data class OnSeriesNameChange(val seriesName: String): AddEditSeriesScreenUiEvents()
    data class OnRoundRobinTimesChange(val roundRobinTimes: String): AddEditSeriesScreenUiEvents()
    data class OnTeamsNameChange(val teamNames: List<String>): AddEditSeriesScreenUiEvents()
}