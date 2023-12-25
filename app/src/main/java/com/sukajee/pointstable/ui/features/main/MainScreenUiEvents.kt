/*
 * Copyright (c) 2023, Sushil Kafle. All rights reserved.
 *
 * This file is part of the Android project authored by Sushil Kafle.
 * Unauthorized copying and using of this file, via any medium, is strictly prohibited.
 * Proprietary and confidential.
 * For inquiries, please contact: info@sukajee.com
 * Last modified by Sushil on Sunday, 24 Dec, 2023.
 */

package com.sukajee.pointstable.ui.features.main

import com.sukajee.pointstable.data.model.Series

sealed class MainScreenUiEvents {
    data class OnCreateUpdateSeriesClick(val series: Series, val isUpdate: Boolean) :
        MainScreenUiEvents()

    data class OnDeleteSeries(val series: Series) : MainScreenUiEvents()
    data object OnUndoDeleteClick : MainScreenUiEvents()
    data object OnDeleteSeriesIgnored : MainScreenUiEvents()
}