package com.sukajee.pointstable.ui.features.enterdata

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
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
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.sukajee.pointstable.ui.components.Chip
import com.sukajee.pointstable.ui.components.ExpandableCard
import com.sukajee.pointstable.utils.getFirstTeam
import com.sukajee.pointstable.utils.getSecondTeam
import com.sukajee.pointstable.utils.getTeamNames

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

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
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

    var filterChipsVisible by rememberSaveable {
        mutableStateOf(false)
    }

    var filterChipTextList by rememberSaveable {
        mutableStateOf(emptyList<String>())
    }

    var selectedChipList by remember {
        mutableStateOf(listOf<String>())
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
        ) {
            var showMenu by remember {
                mutableStateOf(false)
            }
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
                actions = {
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
                                Text(text = if (filterChipsVisible) "Clear filters" else "Filter by team names")
                            },
                            onClick = {
                                filterChipTextList = if (filterChipsVisible) emptyList()
                                else filterChipTextList.toMutableList().apply {
                                    addAll(state.gameList.getTeamNames())
                                }
                                showMenu = !showMenu
                                filterChipsVisible = !filterChipsVisible
                            }
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.Transparent
                )
            )
            if (filterChipsVisible) {
                FlowRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    filterChipTextList.forEach { teamName ->
                        Chip(
                            text = teamName
                        ) { name ->
                            selectedChipList.toMutableList().apply {
                                when {
                                    contains(name) -> remove(name)
                                    size > 1 -> {
                                        removeLast()
                                        add(name)
                                    }
                                    else -> add(name)
                                }
                            }
                        }
                    }
                }
            }
            LazyColumn(
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .weight(1f)
            ) {
                itemsIndexed(
                    state.gameList.filter { game ->
                        if (selectedChipList.isNotEmpty()) {
                            selectedChipList.contains(game.name.getFirstTeam()) ||
                                    selectedChipList.contains(game.name.getSecondTeam())
                        } else true
                    }
                ) { index, game ->
                    ExpandableCard(
                        game = game,
                        expanded = expandedIndex == index,
                        onExpandClick = {
                            expandedState = expandedIndex != index || !expandedState
                            expandedIndex = if (expandedState) index else -1
                        },
                        onUpdateGame = { updatedGame ->
                            onEvent(
                                EnterDataScreenUiEvents.OnUpdateGame(
                                    index, updatedGame
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
                    onClick = onCancelClicked,
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = colors.onSurface
                    )
                ) {
                    Text("Cancel")
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
                    Text("Save Data")
                }
                Spacer(Modifier.width(8.dp))
            }
        }
    }
}