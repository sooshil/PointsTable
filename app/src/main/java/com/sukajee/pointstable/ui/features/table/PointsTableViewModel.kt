/*
 * Copyright (c) 2023, Sushil Kafle. All rights reserved.
 *
 * This file is part of the Android project authored by Sushil Kafle.
 * Unauthorized copying and using of a part or entirety of the code in this file, via any medium, is strictly prohibited.
 * Proprietary and confidential.
 * For inquiries, please contact: info@sukajee.com
 * Last modified by Sushil on Thursday, 28 Dec, 2023.
 */

package com.sukajee.pointstable.ui.features.table

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sukajee.pointstable.data.model.Game
import com.sukajee.pointstable.data.model.PointTableRow
import com.sukajee.pointstable.data.repository.BaseRepository
import com.sukajee.pointstable.utils.round
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PointsTableViewModel @Inject constructor(
    private val repository: BaseRepository,
    private val sharedPreferences: SharedPreferences
) : ViewModel() {
    private val _uiState = MutableStateFlow(PointsTableUiState())
    val uiState = _uiState.asStateFlow()

    init {
        getSeriesList()
    }

    private fun getTableData(seriesId: Int) {
        viewModelScope.launch {
            val games = repository.getGamesBySeriesId(seriesId)
            val pointTableRows = produceTableRows(games)
            _uiState.update { currentState ->
                currentState.copy(
                    isLoading = false,
                    currentSeriesId = seriesId,
                    isMatchDataEmpty = pointTableRows.isEmpty(),
                    pointTableRows = pointTableRows
                )
            }
        }
    }

    private fun getSeriesList() {
        viewModelScope.launch {
            repository.getSeries().collect {
                _uiState.update { currentState ->
                    currentState.copy(
                        seriesList = it
                    )
                }
            }
        }
    }

    private fun produceTableRows(games: List<Game>): List<PointTableRow> {
        val tableRows = mutableListOf<PointTableRow>()
        val teamNames = mutableSetOf<String>().apply {
            games.forEach { game ->
                add(game.firstTeamName)
                add(game.secondTeamName)
            }
        }

        teamNames.forEach { teamName ->
            val totalGameForATeam = games.filter { game ->
                game.firstTeamName == teamName || game.secondTeamName == teamName
            }
            val wonCount = getWonCount(totalGameForATeam, teamName)
            val drawCount = getDrawCount(totalGameForATeam)
            val noResultCount = getNoResultCount(totalGameForATeam)
            tableRows.add(
                PointTableRow(
                    teamName = teamName,
                    played = getPlayedCount(totalGameForATeam, teamName),
                    won = wonCount,
                    lost = getLostCount(totalGameForATeam, teamName),
                    drawn = drawCount,
                    noResult = noResultCount,
                    points = wonCount * 2 + drawCount + noResultCount,
                    netRunRate = getNetRunRate(totalGameForATeam, teamName)
                )
            )
        }
        return tableRows.sortedByDescending { it.netRunRate }.sortedByDescending { it.points }
    }

    private fun getPlayedCount(totalGame: List<Game>, teamName: String): Int {
        return totalGame.count {
            it.isEntryCompleted && (it.firstTeamName == teamName || it.secondTeamName == teamName)
        }
    }

    private fun getWonCount(totalGame: List<Game>, teamName: String): Int {
        return totalGame.count { game ->
            (game.isEntryCompleted &&
                when (teamName) {
                    game.firstTeamName -> (game.teamARuns.toIntOrNull()
                        ?: 0) > (game.teamBRuns.toIntOrNull() ?: 0)

                    game.secondTeamName -> (game.teamBRuns.toIntOrNull()
                        ?: 0) > (game.teamARuns.toIntOrNull() ?: 0)

                    else -> false
                }) ||
                (game.teamAWonInSuperOver == 1 && teamName == game.firstTeamName) ||
                (game.teamAWonInSuperOver == 0 && teamName == game.secondTeamName)
        }
    }

    private fun getLostCount(totalGame: List<Game>, teamName: String): Int {
        return totalGame.count {
            it.isEntryCompleted &&
                when (teamName) {
                    it.firstTeamName -> (it.teamARuns.toIntOrNull()
                        ?: 0) < (it.teamBRuns.toIntOrNull() ?: 0)

                    it.secondTeamName -> (it.teamBRuns.toIntOrNull()
                        ?: 0) < (it.teamARuns.toIntOrNull() ?: 0)

                    else -> false
                }
        }
    }

    private fun getDrawCount(totalGame: List<Game>): Int {
        return totalGame.count {
            it.isEntryCompleted && it.isTied
        }
    }

    private fun getNoResultCount(totalGame: List<Game>): Int {
        return totalGame.count {
            it.isEntryCompleted && it.isNoResult
        }
    }

    private fun getNetRunRate(totalGame: List<Game>, teamName: String): Double {
        var totalRunsFor = 0
        var totalOversFor = 0.0
        var totalRunsAgainst = 0
        var totalOversAgainst = 0.0
        totalGame.filter {
            it.firstTeamName == teamName || it.secondTeamName == teamName
        }.run {
            forEach {
                if (it.firstTeamName == teamName) {
                    totalRunsFor += (it.teamARuns.toIntOrNull() ?: 0)
                    totalOversFor += getOversInDecimal(
                        it.teamAOvers.toIntOrNull() ?: 0,
                        it.teamABalls.toIntOrNull() ?: 0
                    )
                    totalRunsAgainst += (it.teamBRuns.toIntOrNull() ?: 0)
                    totalOversAgainst += getOversInDecimal(
                        it.teamBOvers.toIntOrNull() ?: 0,
                        it.teamBBalls.toIntOrNull() ?: 0
                    )
                } else if (it.secondTeamName == teamName) {
                    totalRunsFor += (it.teamBRuns.toIntOrNull() ?: 0)
                    totalOversFor += getOversInDecimal(
                        it.teamBOvers.toIntOrNull() ?: 0,
                        it.teamBBalls.toIntOrNull() ?: 0
                    )
                    totalRunsAgainst += (it.teamARuns.toIntOrNull() ?: 0)
                    totalOversAgainst += getOversInDecimal(
                        it.teamAOvers.toIntOrNull() ?: 0,
                        it.teamABalls.toIntOrNull() ?: 0
                    )
                }
            }
        }

        return ((totalRunsFor.toDouble() / totalOversFor) -
            (totalRunsAgainst.toDouble() / totalOversAgainst)).round(3)
    }

    private fun getOversInDecimal(overs: Int, balls: Int): Double = overs + balls.toDouble() / 6

    fun onEvent(event: PointsTableUiEvents) {
        when (event) {
            is PointsTableUiEvents.GetTableData -> {
                getTableData(seriesId = event.seriesId)
            }
        }
    }
}