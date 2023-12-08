package com.sukajee.pointstable.ui.features.enterdata

import com.sukajee.pointstable.data.model.Game

sealed class EnterDataScreenUiEvents {
    data object OnSaveGame: EnterDataScreenUiEvents()
    data class OnUpdateGame(val index: Int, val game: Game): EnterDataScreenUiEvents()
}