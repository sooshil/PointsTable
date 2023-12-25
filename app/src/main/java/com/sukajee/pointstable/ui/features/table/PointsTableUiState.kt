/*
 * Copyright (c) 2023, Sushil Kafle. All rights reserved.
 *
 * This file is part of the Android project authored by Sushil Kafle.
 * Unauthorized copying and using of this file, via any medium, is strictly prohibited.
 * Proprietary and confidential.
 * For inquiries, please contact: info@sukajee.com
 * Last modified by Sushil on Sunday, 24 Dec, 2023.
 */

package com.sukajee.pointstable.ui.features.table

import com.sukajee.pointstable.data.model.PointTableRow
import com.sukajee.pointstable.data.model.Series

data class PointsTableUiState(
    val isLoading: Boolean = true,
    val currentSeriesId: Int? = null,
    val isMatchDataEmpty: Boolean = true,
    val seriesList: List<Series> = emptyList(),
    val pointTableRows: List<PointTableRow> = emptyList()
)