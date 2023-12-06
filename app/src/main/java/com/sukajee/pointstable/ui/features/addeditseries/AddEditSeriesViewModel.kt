package com.sukajee.pointstable.ui.features.addeditseries

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sukajee.pointstable.data.model.Series
import com.sukajee.pointstable.data.repository.BaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditSeriesViewModel @Inject constructor(
    private val repository: BaseRepository
) : ViewModel() {


    private val _uiState = MutableStateFlow(AddEditSeriesUiState())
    val uiState = _uiState.asStateFlow()


    fun getSeriesById(seriesId: Int) {
        viewModelScope.launch {
            val series: Series? = repository.getSeriesById(seriesId)
            series?.let {
                _uiState.update { currentState ->
                    currentState.copy(
                        isLoading = false,
                        isEditModeActive = true,
                        seriesName = it.seriesName,
                        seriesId = it.id,
                        roundRobinCount = it.roundRobinTimes.toString(),
                        teamNames = it.improvedTeams,
                        isError = false
                    )
                }
            } ?: run {
                _uiState.update { currentState ->
                    currentState.copy(
                        isLoading = false,
                        isEditModeActive = false,
                        isError = true
                    )
                }
            }
        }
    }

    fun onEvent(event: AddEditSeriesScreenUiEvents) {
        when (event) {
            is AddEditSeriesScreenUiEvents.OnSeriesNameChange -> {
                _uiState.update { currentState ->
                    currentState.copy(
                        seriesName = event.seriesName
                    )
                }
            }
            is AddEditSeriesScreenUiEvents.OnRoundRobinTimesChange -> {
                _uiState.update { currentState ->
                    currentState.copy(
                        roundRobinCount = event.roundRobinTimes
                    )
                }
            }
            is AddEditSeriesScreenUiEvents.OnTeamsNameChange -> {
                _uiState.update { currentState ->
                    currentState.copy(
                        teamNames = event.teamNames
                    )
                }
            }
        }
    }

    fun createUpdateSeries(series: Series) {
        if (series.id == 0) {
            viewModelScope.launch {
                repository.insertSeries(series)
            }
        } else {
            viewModelScope.launch {
                repository.updateSeries(series)
            }
        }
    }
}