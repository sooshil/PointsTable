/*
 * Copyright (c) 2023, Sushil Kafle. All rights reserved.
 *
 * This file is part of the Android project authored by Sushil Kafle.
 * Unauthorized copying and using of a part or entirety of the code in this file, via any medium, is strictly prohibited.
 * Proprietary and confidential.
 * For inquiries, please contact: info@sukajee.com
 * Last modified by Sushil on Saturday, 30 Dec, 2023.
 */

package com.sukajee.pointstable.utils

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

const val SHARED_PREFS = "PointsTableSharedPreferences"
const val SHARED_PREFS_EDIT_DISABLED_SERIES = "EditDisabledSeries"

val noOp: () -> Unit = {}

sealed class BulletStyle {
    data object Numbered : BulletStyle()
    data object BulletIcon : BulletStyle()
    data object None : BulletStyle()
}

enum class IndentLevel(val indent: Dp) {
    None(indent = 0.dp),
    First(indent = 30.dp),
    Second(indent = 60.dp),
    Third(indent = 90.dp)
}