/*
 * Copyright (c) 2023, Sushil Kafle. All rights reserved.
 *
 * This file is part of the Android project authored by Sushil Kafle.
 * Unauthorized copying and using of a part or entirety of the code in this file, via any medium, is strictly prohibited.
 * Proprietary and confidential.
 * For inquiries, please contact: info@sukajee.com
 * Last modified by Sushil on Friday, 29 Dec, 2023.
 */

package com.sukajee.pointstable.ui.features.dataentryhelp

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class DataEntryHelpViewModel @Inject constructor() : ViewModel() {


    private val _uiState = MutableStateFlow(DataEntryHelpUiState())
    val uiState = _uiState.asStateFlow()


}