package com.sukajee.pointstable.ui.features.main

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import com.sukajee.pointstable.data.repository.BaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: BaseRepository,
    private val sharedPreferences: SharedPreferences
): ViewModel() {


}