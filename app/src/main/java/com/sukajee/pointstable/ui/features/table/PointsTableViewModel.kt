package com.sukajee.pointstable.ui.features.table

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sukajee.pointstable.data.model.GameSaveable
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

    fun getTableData(seriesId: Int) {
        viewModelScope.launch {
            val gameSaveables = repository.getGamesBySeriesId(seriesId)
            val pointTableRows = produceTableRows(gameSaveables)
            _uiState.update { currentState ->
                currentState.copy(
                    isLoading = false,
                    isMatchDataEmpty = pointTableRows.isEmpty(),
                    pointTableRows = pointTableRows
                )
            }
        }
    }

    private fun produceTableRows(gameSaveables: List<GameSaveable>): List<PointTableRow> {
        val tableRows = mutableListOf<PointTableRow>()
        val teamNames = mutableSetOf<String>().apply {
            gameSaveables.forEach { game ->
                add(game.firstTeamName)
                add(game.secondTeamName)
            }
        }

        teamNames.forEach { teamName ->
            val totalGameForATeam = gameSaveables.filter { game ->
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
        return tableRows.sortedByDescending { it.points }.sortedByDescending { it.netRunRate }
    }

    private fun getPlayedCount(totalGame: List<GameSaveable>, teamName: String): Int {
        return totalGame.count {
            it.isCompleted && (it.firstTeamName == teamName || it.secondTeamName == teamName)
        }
    }

    private fun getWonCount(totalGame: List<GameSaveable>, teamName: String): Int {
        return totalGame.count {
            it.isCompleted &&
                    ((it.firstTeamName == teamName && it.teamARuns > it.teamBRuns) ||
                            (it.secondTeamName == teamName && it.teamBRuns > it.teamARuns))
        }
    }

    private fun getLostCount(totalGame: List<GameSaveable>, teamName: String): Int {
        return totalGame.count {
            it.isCompleted &&
                    ((it.firstTeamName == teamName && it.teamARuns < it.teamBRuns) ||
                            (it.secondTeamName == teamName && it.teamBRuns < it.teamARuns))
        }
    }

    private fun getDrawCount(totalGame: List<GameSaveable>): Int {
        return totalGame.count {
            it.isCompleted && it.isTied
        }
    }

    private fun getNoResultCount(totalGame: List<GameSaveable>): Int {
        return totalGame.count {
            it.isCompleted && it.isNoResult
        }
    }

    private fun getNetRunRate(totalGame: List<GameSaveable>, teamName: String): Double {
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
}