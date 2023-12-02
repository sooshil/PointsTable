package com.sukajee.pointstable.ui.features.enterdata

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@Composable
fun EnterDataScreen(
    navController: NavController,
    viewModel: EnterDataViewModel,
    seriesId: Int?
) {

    seriesId?.let {
        viewModel.getSeriesById(seriesId = it)
    }

    val state by viewModel.uiState.collectAsState()

    StateLessEnterDataScreen(
        state = state,
        onEvent = {
            viewModel.onEvent(it)
        }
    )
}

@Composable
fun StateLessEnterDataScreen(
    state: EnterDataUiState,
    onEvent: (EnterDataScreenUiEvents) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "${state.series}"
        )
    }
}