/*
 * Copyright (c) 2023, Sushil Kafle. All rights reserved.
 *
 * This file is part of the Android project authored by Sushil Kafle.
 * Unauthorized copying and using of a part or entirety of the code in this file, via any medium, is strictly prohibited.
 * Proprietary and confidential.
 * For inquiries, please contact: info@sukajee.com
 * Last modified by Sushil on Thursday, 28 Dec, 2023.
 */

package com.sukajee.pointstable.ui.features.enterdata

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sukajee.pointstable.data.model.Game
import com.sukajee.pointstable.data.model.Series
import com.sukajee.pointstable.data.repository.BaseRepository
import com.sukajee.pointstable.utils.SHARED_PREFS_EDIT_DISABLED_SERIES
import com.sukajee.pointstable.utils.generateMatchNames
import com.sukajee.pointstable.utils.getFirstTeam
import com.sukajee.pointstable.utils.getSecondTeam
import com.sukajee.pointstable.utils.insertSeriesId
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EnterDataViewModel @Inject constructor(
    private val repository: BaseRepository,
    private val sharedPreferences: SharedPreferences
) : ViewModel() {


    private val _uiState = MutableStateFlow(EnterDataUiState())
    val uiState = _uiState.asStateFlow()

    private var seriesAlreadyFetched = false

    fun getSeriesById(seriesId: Int) {
        if (seriesAlreadyFetched) return
        viewModelScope.launch {
            val series: Series? = repository.getSeriesById(seriesId)
            series?.let {
                getGameList(it)
                seriesAlreadyFetched = true
            } ?: run {
                _uiState.update { currentState ->
                    currentState.copy(
                        isLoading = false,
                        isError = true
                    )
                }
            }
        }
    }

    private fun getGameList(series: Series) {
        viewModelScope.launch {
            val gameList = repository.getGamesBySeriesId(series.id)
            if (gameList.isEmpty()) {
                _uiState.update { currentState ->
                    currentState.copy(
                        isLoading = false,
                        seriesName = series.seriesName,
                        gameList = generateGameList(series)
                    )
                }
            } else {
                _uiState.update { currentState ->
                    currentState.copy(
                        isLoading = false,
                        seriesName = series.seriesName,
                        gameList = gameList.map {
                            it
                        }
                    )
                }
            }
        }
    }

    private fun generateGameList(series: Series): List<Game> {
        val gameList = mutableListOf<Game>()
        series.generateMatchNames().forEach {
            gameList.add(
                Game(
                    seriesId = series.id,
                    name = it,
                    firstTeamName = it.getFirstTeam(),
                    secondTeamName = it.getSecondTeam(),
                    teamARuns = "",
                    teamAOvers = "",
                    teamABalls = "",
                    teamBRuns = "",
                    teamBOvers = "",
                    teamBBalls = "",
                    isNoResult = false,
                    isTied = false
                )
            )
        }
        return gameList
    }

    fun onEvent(event: EnterDataScreenUiEvents) {
        when (event) {
            is EnterDataScreenUiEvents.OnUpdateGame -> updateGame(event.index, event.game)
            is EnterDataScreenUiEvents.OnSaveGame -> saveGame()
        }
    }

    private fun updateGame(index: Int, game: Game) {
        val gameToSave = if (game.isNoResult) {
            game.copy(
                teamARuns = "0",
                teamAOvers = "0",
                teamABalls = "0",
                teamBRuns = "0",
                teamBOvers = "0",
                teamBBalls = "0",
                isTied = false
            )
        } else game
        val existingGameList = _uiState.value.gameList
        val newGameList = existingGameList.toMutableList().apply {
            this[index] = gameToSave
        }
        _uiState.update { currentState ->
            currentState.copy(
                gameList = newGameList.toList()
            )
        }
    }

    private fun saveGame() {
        val gameList = _uiState.value.gameList
        viewModelScope.launch {
            gameList.forEach { game ->
                repository.insertGame(game)
            }

        }.invokeOnCompletion {
            if (it == null) {
                val currentSeries =
                    sharedPreferences.getString(SHARED_PREFS_EDIT_DISABLED_SERIES, "")
                gameList.firstOrNull()?.seriesId?.let { seriesId ->
                    sharedPreferences.edit().apply {
                        putString(
                            SHARED_PREFS_EDIT_DISABLED_SERIES,
                            currentSeries?.insertSeriesId(seriesId.toString().trim())
                        )
                    }.apply()
                }
            }
        }
    }
}