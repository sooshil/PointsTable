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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.sukajee.pointstable.data.model.Game
import com.sukajee.pointstable.data.model.Series
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
        ) {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Enter Data",
                        fontSize = 24.sp,
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
                        onClick = onBackArrowClick
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Navigate back"
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
                    onClick = onCancelClicked
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
                    }
                ) {
                    Text("Save Data")
                }
                Spacer(Modifier.width(8.dp))
            }
        }
    }
}