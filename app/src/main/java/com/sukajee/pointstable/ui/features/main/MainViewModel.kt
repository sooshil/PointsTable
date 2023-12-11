package com.sukajee.pointstable.ui.features.main

import android.content.SharedPreferences
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
class MainViewModel @Inject constructor(
    private val repository: BaseRepository,
    private val sharedPreferences: SharedPreferences
) : ViewModel() {

    private val _uiState = MutableStateFlow(MainScreenUiState())
    val uiState = _uiState.asStateFlow()

    private var seriesBeingDeleted: Series? = null

    init {
        getSeries()
    }

    private fun getSeries() {
        viewModelScope.launch {
            repository.getSeries().collect {
                _uiState.update { currentState ->
                    currentState.copy(
                        isLoading = false,
                        series = it
                    )
                }
            }
        }
    }

    private fun insertSeries(series: Series) = viewModelScope.launch {
        repository.insertSeries(series = series)
    }

    private fun updateSeries(series: Series) = viewModelScope.launch {
        repository.updateSeries(series = series)
    }

    private fun deleteSeries(series: Series) = viewModelScope.launch {
        repository.deleteSeries(seriesId = series.id)
    }

    fun onEvent(event: MainScreenUiEvents) {
        when (event) {
            is MainScreenUiEvents.OnCreateUpdateSeriesClick -> {
                when (event.isUpdate) {
                    true -> updateSeries(event.series)
                    else -> insertSeries(event.series)
                }
            }
            is MainScreenUiEvents.OnDeleteSeries -> {
                seriesBeingDeleted = event.series
                deleteSeries(series = event.series)
            }
            is MainScreenUiEvents.OnUndoDeleteClick -> {
                seriesBeingDeleted?.let { series ->
                    insertSeries(series = series)
                    seriesBeingDeleted = null
                }
            }
            is MainScreenUiEvents.OnDeleteSeriesIgnored -> seriesBeingDeleted = null
        }
    }
}