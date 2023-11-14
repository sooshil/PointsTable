package com.sukajee.pointstable.ui.features.main

import android.content.SharedPreferences
import androidx.compose.material.icons.materialIcon
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sukajee.pointstable.data.model.Match
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

    val _uiState = MutableStateFlow(MainScreenUiState())
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
                        matches = it
                    )
                }
            }
        }
    }

    fun saveMatch(match: Match) = viewModelScope.launch {
        repository.insertMatch(match = match)
    }

}