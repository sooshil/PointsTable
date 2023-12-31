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
import com.sukajee.pointstable.data.model.ParagraphItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

class DataEntryHelpViewModel @Inject constructor() : ViewModel() {


    private val _uiState = MutableStateFlow(DataEntryHelpUiState())
    val uiState = _uiState.asStateFlow()

    fun updateParagraphs(helpParagraphs: List<ParagraphItem>) {
        _uiState.update { currentState ->
            currentState.copy(
                isLoading = false,
                paragraphs = helpParagraphs
            )
        }
    }
}
