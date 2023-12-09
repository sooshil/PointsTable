package com.sukajee.pointstable.ui.features.addeditseries

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sukajee.pointstable.data.model.Series
import com.sukajee.pointstable.data.repository.BaseRepository
import com.sukajee.pointstable.utils.SHARED_PREFS_EDIT_DISABLED_SERIES
import com.sukajee.pointstable.utils.containsSeriesId
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditSeriesViewModel @Inject constructor(
    private val repository: BaseRepository,
    private val sharedPreferences: SharedPreferences
) : ViewModel() {


    private val _uiState = MutableStateFlow(AddEditSeriesUiState())
    val uiState = _uiState.asStateFlow()

    private val editingDisabledSeriesId = sharedPreferences.getString(SHARED_PREFS_EDIT_DISABLED_SERIES, "")

    fun getSeriesById(seriesId: Int) {
        viewModelScope.launch {
            val series: Series? = repository.getSeriesById(seriesId)
            series?.let {
                _uiState.update { currentState ->
                    currentState.copy(
                        isLoading = false,
                        isEditingDisabled = editingDisabledSeriesId?.containsSeriesId(it.id.toString()) ?: false,
                        isEditModeActive = true,
                        seriesName = it.seriesName,
                        seriesId = it.id,
                        createdAt = it.createdAt.toString(),
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

    fun createUpdateSeries(series: Series, isUpdate: Boolean) {
        if (isUpdate) {
            viewModelScope.launch {
                repository.updateSeries(series)
            }
        } else {
            viewModelScope.launch {
                repository.insertSeries(series)
            }
        }
    }
}