package com.sukajee.pointstable.ui.features.enterdata

import com.sukajee.pointstable.data.model.Game
import com.sukajee.pointstable.utils.getFirstTeam
import com.sukajee.pointstable.utils.getSecondTeam

data class EnterDataUiState(
    val isLoading: Boolean = true,
    val isError: Boolean = false,
    val seriesName: String = "",
    val gameList: List<Game> = emptyList()
)