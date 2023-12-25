/*
 * Copyright (c) 2023, Sushil Kafle. All rights reserved.
 *
 * This file is part of the Android project authored by Sushil Kafle.
 * Unauthorized copying and using of this file, via any medium, is strictly prohibited.
 * Proprietary and confidential.
 * For inquiries, please contact: info@sukajee.com
 * Last modified by Sushil on Sunday, 24 Dec, 2023.
 */

package com.sukajee.pointstable.ui.features.addeditseries

data class AddEditSeriesUiState(
    val isLoading: Boolean = true,
    val isEditingDisabled: Boolean = false,
    val isEditModeActive: Boolean = false,
    val seriesId: Int = -1,
    val seriesName: String = "",
    val createdAt: String = "",
    val updatedAt: String = "",
    val roundRobinCount: String = "",
    val teamNames: List<String> = emptyList(),
    val isError: Boolean = false,
)
