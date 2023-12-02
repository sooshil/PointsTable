package com.sukajee.pointstable.ui.features.enterdata

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
class EnterDataViewModel @Inject constructor(
    private val repository: BaseRepository,
    private val sharedPreferences: SharedPreferences
) : ViewModel() {


    private val _uiState = MutableStateFlow(EnterDataUiState())
    val uiState = _uiState.asStateFlow()


    fun getSeriesById(seriesId: Int) {
        viewModelScope.launch {
            val series: Series? = repository.getSeriesById(seriesId)
            series?.let {
                _uiState.update {currentState ->
                    currentState.copy(
                        isLoading = false,
                        series = it
                    )
                }
            } ?: run {
                _uiState.update {currentState ->
                    currentState.copy(
                        isLoading = false,
                        isError = true
                    )
                }
            }
        }
    }

    fun onEvent(event: EnterDataScreenUiEvents) {

    }
}