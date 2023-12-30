/*
 * Copyright (c) 2023, Sushil Kafle. All rights reserved.
 *
 * This file is part of the Android project authored by Sushil Kafle.
 * Unauthorized copying and using of a part or entirety of the code in this file, via any medium, is strictly prohibited.
 * Proprietary and confidential.
 * For inquiries, please contact: info@sukajee.com
 * Last modified by Sushil on Friday, 29 Dec, 2023.
 */

package com.sukajee.pointstable.utils

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

const val SHARED_PREFS = "PointsTableSharedPreferences"
const val SHARED_PREFS_EDIT_DISABLED_SERIES = "EditDisabledSeries"


sealed class Indent {
    data class NumberIndent(val level: IndentLevel) : Indent()
    data class BulletIndent(val level: IndentLevel) : Indent()
}

enum class IndentLevel(val indent: Dp) {
    FIRST(indent = 0.dp),
    SECOND(indent = 24.dp),
    THIRD(indent = 48.dp)
}