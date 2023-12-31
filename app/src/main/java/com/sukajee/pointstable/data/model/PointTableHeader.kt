/*
 * Copyright (c) 2023, Sushil Kafle. All rights reserved.
 *
 * This file is part of the Android project authored by Sushil Kafle.
 * Unauthorized copying and using of a part or entirety of the code in this file, via any medium, is strictly prohibited.
 * Proprietary and confidential.
 * For inquiries, please contact: info@sukajee.com
 * Last modified by Sushil on Monday, 25 Dec, 2023.
 */

package com.sukajee.pointstable.data.model

import androidx.compose.runtime.Stable

@Stable
data class PointTableHeader(
    val name: String,
    val description: String?,
    val columnWidth: Double = 0.9
)
