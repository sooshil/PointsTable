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

    init {
        getMatches()
    }

    private fun getMatches() {
        viewModelScope.launch {
            repository.getMatches().collect {
                _uiState.update { currentState ->
                    currentState.copy(
                        isLoading = false,
                        series = it
                    )
                }
            }
        }
    }

    private fun insertMatch(series: Series) = viewModelScope.launch {
        repository.insertMatch(series = series)
    }

    fun onEvent(event: MainScreenUiEvents) {
        when(event) {
            is MainScreenUiEvents.OnInsertSeriesClick -> {
                insertMatch(event.series)
            }
        }
    }

}