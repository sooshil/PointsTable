package com.sukajee.pointstable.ui.features.addseries

import android.util.Log
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
        Log.d("TAG", "getSeriesById: $seriesId")
        viewModelScope.launch {
            val series: Series? = repository.getSeriesById(seriesId)
            series?.let {
                _uiState.update { currentState ->
                    currentState.copy(
                        isLoading = false,
                        isEditModeActive = true,
                        seriesBeingEdited = it
                    )
                }
                Log.d("TAG", "updatedState: ${uiState.value}")
            } ?: run {
                _uiState.update { currentState ->
                    currentState.copy(
                        isLoading = false,
                        isEditModeActive = false,
                        seriesBeingEdited = null,
                        isError = true
                    )
                }
            }
        }
    }

    fun onEvent(event: AddEditSeriesScreenUiEvents) {

    }
}