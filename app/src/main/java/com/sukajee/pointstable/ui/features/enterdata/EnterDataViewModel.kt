package com.sukajee.pointstable.ui.features.enterdata

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sukajee.pointstable.data.model.Game
import com.sukajee.pointstable.data.model.ScoreData
import com.sukajee.pointstable.data.model.Series
import com.sukajee.pointstable.data.repository.BaseRepository
import com.sukajee.pointstable.utils.generateMatchNames
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
                _uiState.update { currentState ->
                    currentState.copy(
                        isLoading = false,
                        gameList = generateGameList(it)
                    )
                }
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

    private fun generateGameList(series: Series): List<Game> {
        val gameList = mutableListOf<Game>()
        series.generateMatchNames().forEach {
            gameList.add(
                Game(
                    seriesId = series.id,
                    name = it,
                    scoreData = ScoreData(
                        teamARuns = "",
                        teamAOvers = "",
                        teamABalls = "",
                        teamBRuns = "",
                        teamBOvers = "",
                        teamBBalls = ""
                    ),
                    isNoResult = false,
                    isTied = false
                )
            )
        }
        return gameList
    }

    fun onEvent(event: EnterDataScreenUiEvents) {
        when (event) {
            is EnterDataScreenUiEvents.OnUpdateGame -> {
                updateGame(event.index, event.game)
            }
            is EnterDataScreenUiEvents.OnSaveGame -> {
                saveGame()
            }
        }
    }

    fun updateGame(index: Int, game: Game) {
        val existingGameList = _uiState.value.gameList
        val newGameList = existingGameList.toMutableList().apply {
            this[index] = game
        }
        _uiState.update { currentState ->
            currentState.copy(
                gameList = newGameList.toList()
            )
        }
    }

    fun saveGame() {

    }
}