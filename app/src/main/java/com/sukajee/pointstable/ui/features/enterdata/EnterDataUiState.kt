package com.sukajee.pointstable.ui.features.enterdata

import com.sukajee.pointstable.data.model.Game

data class EnterDataUiState(
    val isLoading: Boolean = true,
    val isError: Boolean = false,
    val gameList: List<Game> = emptyList()
)