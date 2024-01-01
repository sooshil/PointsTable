/*
 * Copyright (c) 2023, Sushil Kafle. All rights reserved.
 *
 * This file is part of the Android project authored by Sushil Kafle.
 * Unauthorized copying and using of a part or entirety of the code in this file, via any medium, is strictly prohibited.
 * Proprietary and confidential.
 * For inquiries, please contact: info@sukajee.com
 * Last modified by Sushil on Sunday, 31 Dec, 2024.
 */

package com.sukajee.pointstable.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sukajee.pointstable.utils.BulletStyle
import com.sukajee.pointstable.utils.IndentLevel
import com.sukajee.pointstable.utils.noOp

@Composable
fun IndentedParagraph(
    modifier: Modifier = Modifier,
    text: String,
    isBold: Boolean = false,
    itemNumber: String? = null,
    bulletStyle: BulletStyle,
    indentLevel: IndentLevel
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .wrapContentHeight(),
        ) {
            if (bulletStyle != BulletStyle.None) {
                Spacer(modifier = Modifier.width(indentLevel.indent))
                Box(
                    modifier = Modifier
                        .width(30.dp),
                    contentAlignment = Alignment.TopStart
                ) {
                    when (bulletStyle) {
                        BulletStyle.BulletIcon -> TODO()
                        BulletStyle.Numbered -> {
                            Text(
                                text = itemNumber?.let { "$it." } ?: "",
                                fontWeight = if (isBold) FontWeight.Bold else FontWeight.Normal,
                            )
                        }

                        BulletStyle.None -> noOp // Unreachable
                    }
                }
            }
            Text(
                text = text,
                textAlign = TextAlign.Justify,
                fontWeight = if (isBold) FontWeight.Bold else FontWeight.Normal,
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun IndentedParagraphPreview() {
    Column {
        IndentedParagraph(
            text = "This is a paragraph. This is just a sample text. But it can grow longer over time. We can't imagine how long it can go. It can go even longer.",
            itemNumber = "2",
            bulletStyle = BulletStyle.Numbered,
            indentLevel = IndentLevel.None,
        )
        IndentedParagraph(
            text = "This is a paragraph. This is just a sample text. But it can grow longer over time. We can't imagine how long it can go. It can go even longer.",
            itemNumber = "22",
            bulletStyle = BulletStyle.Numbered,
            indentLevel = IndentLevel.First,
        )
        IndentedParagraph(
            text = "This is a paragraph. This is just a sample text. But it can grow longer over time. We can't imagine how long it can go. It can go even longer.",
            itemNumber = null,
            bulletStyle = BulletStyle.Numbered,
            indentLevel = IndentLevel.First,
        )
        IndentedParagraph(
            text = "This is a paragraph. This is just a sample text. But it can grow longer over time. We can't imagine how long it can go. It can go even longer.",
            itemNumber = "22",
            bulletStyle = BulletStyle.Numbered,
            indentLevel = IndentLevel.Third,
        )
    }
}