/*
 * Copyright (c) 2023, Sushil Kafle. All rights reserved.
 *
 * This file is part of the Android project authored by Sushil Kafle.
 * Unauthorized copying and using of a part or entirety of the code in this file, via any medium, is strictly prohibited.
 * Proprietary and confidential.
 * For inquiries, please contact: info@sukajee.com
 * Last modified by Sushil on Monday, 25 Dec, 2023.
 */

package com.sukajee.pointstable.ui.features.enterdata

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.sukajee.pointstable.R
import com.sukajee.pointstable.ui.components.ExpandableCard

@Composable
fun EnterDataScreen(
    navController: NavController,
    viewModel: EnterDataViewModel,
    seriesId: Int?
) {
    LaunchedEffect(key1 = true) {
        seriesId?.let {
            viewModel.getSeriesById(seriesId = it)
        }
    }

    val state by viewModel.uiState.collectAsState()

    StateLessEnterDataScreen(
        state = state,
        onEvent = {
            viewModel.onEvent(it)
        },
        onBackArrowClick = {
            navController.popBackStack()
        },
        onCancelClicked = {
            navController.popBackStack()
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StateLessEnterDataScreen(
    state: EnterDataUiState,
    onBackArrowClick: () -> Unit,
    onCancelClicked: () -> Unit,
    onEvent: (EnterDataScreenUiEvents) -> Unit
) {
    val colors = MaterialTheme.colorScheme
    var expandedState by rememberSaveable {
        mutableStateOf(false)
    }
    var expandedIndex by rememberSaveable {
        mutableIntStateOf(-1)
    }

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
                    painterResource(id = R.drawable.cricket_ball),
                    contentScale = ContentScale.FillWidth,
                    alpha = 0.07f
                )
        ) {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = state.seriesName.ifBlank { "Enter Data" },
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        color = colors.onSurface
                    )
                },
                navigationIcon = {
                    IconButton(
                        modifier = Modifier.background(
                            color = colors.onSurface.copy(
                                alpha = 0.03f
                            ), shape = CircleShape
                        ),
                        onClick = onBackArrowClick
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Navigate back",
                            tint = colors.onSurface
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
                itemsIndexed(state.gameList) { index, game ->
                    ExpandableCard(
                        game = game,
                        expanded = expandedIndex == index,
                        onExpandClick = {
                            expandedState = expandedIndex != index || !expandedState
                            expandedIndex = if (expandedState) index else -1
                        },
                        onUpdateGame = { updatedGame ->
                            val gameToUpdate = updatedGame.copy(
                                isCompleted = updatedGame.isEntryCompleted
                            )
                            onEvent(
                                EnterDataScreenUiEvents.OnUpdateGame(
                                    index, gameToUpdate
                                )
                            )
                        }
                    )
                    Spacer(Modifier.height(8.dp))
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 24.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                OutlinedButton(
                    modifier = Modifier.weight(1f),
                    onClick = onCancelClicked
                ) {
                    Text(text = "Cancel", textAlign = TextAlign.Center)
                }
                Spacer(Modifier.width(8.dp))
                Button(
                    modifier = Modifier.weight(1f),
                    onClick = {
                        onEvent(
                            EnterDataScreenUiEvents.OnSaveGame
                        )
                        onBackArrowClick()
                    },
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = colors.onPrimary,
                        containerColor = colors.primary
                    )
                ) {
                    Text(text = "Save Data", textAlign = TextAlign.Center)
                }
                Spacer(Modifier.width(8.dp))
            }
        }
    }
}