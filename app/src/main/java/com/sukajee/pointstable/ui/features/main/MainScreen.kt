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

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.sukajee.pointstable.navigation.Screen
import com.sukajee.pointstable.ui.components.SeriesComponent
import kotlinx.coroutines.launch

@Composable
fun MainScreen(
    navController: NavController,
    viewModel: MainViewModel
) {
    val state by viewModel.uiState.collectAsState()
    StateLessMainScreen(
        state = state,
        onEvent = {
            viewModel.onEvent(it)
        },
        onEnterDataClicked = {
            navController.navigate(
                Screen.EnterDataScreen.route.plus("/$it")
            )
        },
        onSeriesCardClicked = {
            navController.navigate(
                Screen.AddEditSeriesScreen.route.plus("?seriesId=$it")
            )
        },
        onTableClicked = { seriesId ->
            navController.navigate(
                Screen.PointsTableScreen.route.plus("?seriesId=$seriesId")
            )
        },
        onCreateGameClicked = {
            navController.navigate(
                Screen.AddEditSeriesScreen.route
            )
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun StateLessMainScreen(
    state: MainScreenUiState,
    onEnterDataClicked: (seriesId: Int) -> Unit,
    onSeriesCardClicked: (seriesId: Int) -> Unit,
    onTableClicked: (seriesId: Int) -> Unit,
    onEvent: (MainScreenUiEvents) -> Unit,
    onCreateGameClicked: () -> Unit
) {
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding()
                .padding(
                    start = it.calculateStartPadding(LayoutDirection.Rtl),
                    end = it.calculateEndPadding(LayoutDirection.Rtl)
                )
        ) {
            var showMenu by remember {
                mutableStateOf(false)
            }

            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Series",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                navigationIcon = {
                    IconButton(
                        modifier = Modifier.background(
                            color = MaterialTheme.colorScheme.onSurface.copy(
                                alpha = 0.03f
                            ), shape = CircleShape
                        ),
                        onClick = {

                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = "Menu",
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = onCreateGameClicked
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Create game",
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                    IconButton(
                        onClick = {
                            showMenu = true
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.MoreVert,
                            contentDescription = "Create game",
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                    DropdownMenu(
                        expanded = showMenu,
                        onDismissRequest = {
                            showMenu = false
                        }
                    ) {
                        DropdownMenuItem(
                            text = {
                                Text(text = "Sort by created date")
                            },
                            onClick = {

                            }
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.Transparent,
                    actionIconContentColor = MaterialTheme.colorScheme.onSurface
                )
            )
            LazyColumn(
                modifier = Modifier.padding(horizontal = 8.dp)
            ) {
                items(items = state.series, key = { series -> series.id }) { series ->
                    SeriesComponent(
                        modifier = Modifier.animateItemPlacement(
                            animationSpec = tween(
                                durationMillis = 1000,
                                easing = LinearOutSlowInEasing
                            )
                        ),
                        series = series,
                        onCardClick = {
                            onSeriesCardClicked(series.id)
                        },
                        onTableClick = {
                            onTableClicked(series.id)
                        },
                        onEnterDataClick = {
                            onEnterDataClicked(series.id)
                        },
                        onDeleteSeries = { seriesBeingDeleted ->
                            scope.launch {
                                val result = snackbarHostState.showSnackbar(
                                    message = "Series deleted",
                                    actionLabel = "Undo",
                                    duration = SnackbarDuration.Long
                                )
                                when (result) {
                                    SnackbarResult.Dismissed -> {
                                        onEvent(
                                            MainScreenUiEvents.OnDeleteSeriesIgnored
                                        )
                                    }

                                    SnackbarResult.ActionPerformed -> {
                                        onEvent(
                                            MainScreenUiEvents.OnUndoDeleteClick
                                        )
                                    }
                                }
                            }
                            onEvent(
                                MainScreenUiEvents.OnDeleteSeries(seriesBeingDeleted)
                            )
                        }
                    )
                    Spacer(modifier = Modifier.padding(vertical = 4.dp))
                }
            }
        }
    }
}