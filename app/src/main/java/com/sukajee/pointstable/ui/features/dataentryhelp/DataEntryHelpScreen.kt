/*
 * Copyright (c) 2023, Sushil Kafle. All rights reserved.
 *
 * This file is part of the Android project authored by Sushil Kafle.
 * Unauthorized copying and using of a part or entirety of the code in this file, via any medium, is strictly prohibited.
 * Proprietary and confidential.
 * For inquiries, please contact: info@sukajee.com
 * Last modified by Sushil on Saturday, 30 Dec, 2023.
 */

package com.sukajee.pointstable.ui.features.dataentryhelp

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.sukajee.pointstable.R
import com.sukajee.pointstable.data.model.ParagraphItem
import com.sukajee.pointstable.ui.components.IndentedParagraph
import com.sukajee.pointstable.utils.BulletStyle
import com.sukajee.pointstable.utils.IndentLevel

@Composable
fun DataEntryHelpScreen(
    navController: NavController,
    viewModel: DataEntryHelpViewModel
) {
    var isDataSet by rememberSaveable {
        mutableStateOf(false)
    }
    val context = LocalContext.current
    if (isDataSet.not()) {
        LaunchedEffect(key1 = true) {
            isDataSet = true
            viewModel.updateParagraphs(
                getHelpParagraphs(context = context)
            )
        }
    }
    val state by viewModel.uiState.collectAsState()

    StateLessDataEntryHelpScreen(
        state = state,
        onBackArrowClick = {
            navController.popBackStack()
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StateLessDataEntryHelpScreen(
    state: DataEntryHelpUiState,
    onBackArrowClick: () -> Unit,
) {
    Scaffold {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding()
                .padding(
                    start = it.calculateStartPadding(LayoutDirection.Rtl),
                    end = it.calculateEndPadding(LayoutDirection.Rtl)
                )
                .imePadding()
                .paint(
                    painterResource(id = R.drawable.player),
                    contentScale = ContentScale.FillWidth,
                    alpha = 0.07f
                )
        ) {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Data Entry Help",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                },
                navigationIcon = {
                    IconButton(
                        modifier = Modifier.background(
                            color = MaterialTheme.colorScheme.onSurface.copy(
                                alpha = 0.03f
                            ), shape = CircleShape
                        ),
                        onClick = onBackArrowClick
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Navigate back",
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.Transparent
                )
            )
            LazyColumn(
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .weight(1f)
            ) {
                items(
                    items = state.paragraphs
                ) { paragraphItem ->
                    IndentedParagraph(
                        text = paragraphItem.text,
                        itemNumber = paragraphItem.itemNumber,
                        bulletStyle = paragraphItem.bulletStyle,
                        indentLevel = paragraphItem.indentLevel
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}

fun getHelpParagraphs(context: Context) =
    listOf(
        ParagraphItem(
            text = "This is a paragraph. This is just a sample text. But it can grow longer over time. We can't imagine how long it can go. It can go even longer.",
            itemNumber = 2,
            bulletStyle = BulletStyle.Numbered,
            indentLevel = IndentLevel.None,
        ),
        ParagraphItem(
            text =
            "This is a paragraph. This is just a sample text. But it can grow longer over time. We can't imagine how long it can go. It can go even longer.",
            itemNumber = 22,
            bulletStyle = BulletStyle.Numbered,
            indentLevel = IndentLevel.First,
        ),
        ParagraphItem(
            text = "This is a paragraph. This is just a sample text. But it can grow longer over time. We can't imagine how long it can go. It can go even longer.",
            itemNumber = 22,
            bulletStyle = BulletStyle.Numbered,
            indentLevel = IndentLevel.Second,
        ),
        ParagraphItem(
            text = "This is a paragraph. This is just a sample text. But it can grow longer over time. We can't imagine how long it can go. It can go even longer.",
            itemNumber = 22,
            bulletStyle = BulletStyle.Numbered,
            indentLevel = IndentLevel.Third,
        )
    )

