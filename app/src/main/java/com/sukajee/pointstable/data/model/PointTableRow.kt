/*
 * Copyright (c) 2023, Sushil Kafle. All rights reserved.
 *
 * This file is part of the Android project authored by Sushil Kafle.
 * Unauthorized copying and using of this file, via any medium, is strictly prohibited.
 * Proprietary and confidential.
 * For inquiries, please contact: info@sukajee.com
 * Last modified by Sushil on Sunday, 24 Dec, 2023.
 */

package com.sukajee.pointstable.data.model

data class PointTableRow(
    val teamName: String,
    val played: Int,
    val won: Int,
    val lost: Int,
    val drawn: Int,
    val noResult: Int,
    val points: Int,
    val netRunRate: Double
)
