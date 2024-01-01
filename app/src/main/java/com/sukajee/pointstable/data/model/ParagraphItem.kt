/*
 * Copyright (c) 2023-2024, Sushil Kafle. All rights reserved.
 *
 * This file is part of the Android project authored by Sushil Kafle.
 * Unauthorized copying and using of a part or entirety of the code in this file, via any medium, is strictly prohibited.
 * Proprietary and confidential.
 * For inquiries, please contact: info@sukajee.com
 * Last modified by Sushil on Monday, 01 Jan, 2024.
 */

package com.sukajee.pointstable.data.model

import com.sukajee.pointstable.utils.BulletStyle
import com.sukajee.pointstable.utils.IndentLevel

data class ParagraphItem(
    val text: String,
    val itemNumber: String? = null,
    val isBold: Boolean = false,
    val bulletStyle: BulletStyle = BulletStyle.Numbered,
    val indentLevel: IndentLevel = IndentLevel.None
)