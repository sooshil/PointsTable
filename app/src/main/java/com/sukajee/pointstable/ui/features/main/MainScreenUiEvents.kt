package com.sukajee.pointstable.ui.features.main

import com.sukajee.pointstable.data.model.Match

sealed class MainScreenUiEvents {
    data class OnInsertMatchClick(val match: Match): MainScreenUiEvents()
}