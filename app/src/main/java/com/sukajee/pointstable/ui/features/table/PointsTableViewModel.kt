package com.sukajee.pointstable.ui.features.table

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sukajee.pointstable.data.model.GameSaveable
import com.sukajee.pointstable.data.model.PointTableRow
import com.sukajee.pointstable.data.repository.BaseRepository
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
            val drawCount = getDrawCount(totalGameForATeam, teamName)
            tableRows.add(
                PointTableRow(
                    teamName = teamName,
                    played = getPlayedCount(totalGameForATeam, teamName),
                    won = wonCount,
                    lost = getLostCount(totalGameForATeam, teamName),
                    drawn = drawCount,
                    noResult = getNoResultCount(totalGameForATeam, teamName),
                    points = wonCount * 2 + drawCount,
                    netRunRate = 0.0
                )
            )
        }
        return tableRows
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

    private fun getDrawCount(totalGame: List<GameSaveable>, teamName: String): Int {
        return totalGame.count {
            it.isCompleted && it.isTied
        }
    }

    private fun getNoResultCount(totalGame: List<GameSaveable>, teamName: String): Int {
        return totalGame.count {
            it.isCompleted && it.isNoResult
        }
    }

}