package com.sukajee.pointstable.ui.features.addeditseries

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.sukajee.pointstable.data.model.Series

@Composable
fun AddEditSeriesScreen(
    navController: NavController,
    viewModel: AddEditSeriesViewModel,
    seriesId: Int?
) {
    LaunchedEffect(key1 = true) {
        seriesId?.let {
            viewModel.getSeriesById(seriesId = it)
        }
    }

    val state by viewModel.uiState.collectAsState()

    StateLessAddEditSeriesScreen(
        state = state,
        onBackArrowClick = {
            navController.popBackStack()
        },
        onCancelClicked = {
            navController.popBackStack()
        },
        onCreateUpdateSeriesClicked = { series, isUpdate ->
            viewModel.createUpdateSeries(series, isUpdate)
            navController.popBackStack()
        },
        onEvent = {
            viewModel.onEvent(it)
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StateLessAddEditSeriesScreen(
    state: AddEditSeriesUiState,
    onBackArrowClick: () -> Unit,
    onCancelClicked: () -> Unit,
    onCreateUpdateSeriesClicked: (series: Series, isUpdate: Boolean) -> Unit,
    onEvent: (AddEditSeriesScreenUiEvents) -> Unit
) {
    Scaffold {
        var isErrorInSeriesName by rememberSaveable { mutableStateOf(false) }
        var insufficientTeams by rememberSaveable { mutableStateOf(false) }

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
                        text = if (state.isEditModeActive) "Edit Series" else "Create a Series",
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
                modifier = Modifier.padding(horizontal = 8.dp)
            ) {
                item {
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        modifier = Modifier
                            .fillMaxWidth(),
                        value = state.seriesName,
                        onValueChange = { name ->
                            onEvent(AddEditSeriesScreenUiEvents.OnSeriesNameChange(name))
                            isErrorInSeriesName = false
                        },
                        maxLines = 1,
                        label = {
                            Text(text = "Series Name")
                        },
                        isError = isErrorInSeriesName,
                        supportingText = {
                            if (isErrorInSeriesName) {
                                Text(
                                    modifier = Modifier.fillMaxWidth(),
                                    text = "Series name cannot be empty",
                                    color = MaterialTheme.colorScheme.error
                                )
                            }
                        },
                        trailingIcon = {
                            if (isErrorInSeriesName)
                                Icon(
                                    Icons.Filled.Warning,
                                    "error",
                                    tint = MaterialTheme.colorScheme.error
                                )
                        }
                    )
                }
                item {
                    Spacer(modifier = Modifier.height(4.dp))
                    OutlinedTextField(
                        modifier = Modifier
                            .fillMaxWidth(),
                        value = state.roundRobinCount,
                        onValueChange = { newValue ->
                            onEvent(AddEditSeriesScreenUiEvents.OnRoundRobinTimesChange(newValue))
                        },
                        maxLines = 1,
                        label = {
                            Text(text = "Round-robin times")
                        },
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.NumberPassword
                        ),
                        visualTransformation = VisualTransformation.None
                    )
                }
                items(state.teamNames.size) { index ->
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        modifier = Modifier
                            .fillMaxWidth(),
                        value = state.teamNames[index],
                        onValueChange = { team ->
                            val existingTeams = state.teamNames.toMutableList()
                            existingTeams[index] = team
                            onEvent(AddEditSeriesScreenUiEvents.OnTeamsNameChange(existingTeams.toList()))
                        },
                        maxLines = 1,
                        label = {
                            Text(text = "Team ${index + 1}")
                        }
                    )
                }
                if (insufficientTeams) {
                    item {
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            text = "At least two teams are required to proceed. Add teams",
                            color = MaterialTheme.colorScheme.error,
                            fontSize = 12.sp
                        )
                    }
                }
                item {
                    Spacer(modifier = Modifier.height(8.dp))
                    TextButton(
                        modifier = Modifier
                            .fillMaxWidth(),
                        onClick = {
                            insufficientTeams = false
                            val existingTeams = state.teamNames.toMutableList()
                            existingTeams.add("")
                            onEvent(AddEditSeriesScreenUiEvents.OnTeamsNameChange(existingTeams.toList()))
                        },
                    ) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = "Add Team",
                            fontSize = 14.sp,
                            textAlign = TextAlign.Start
                        )
                    }
                }
                item {
                    Spacer(modifier = Modifier.height(16.dp))
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
                                if (state.seriesName.isEmpty()) {
                                    isErrorInSeriesName = true
                                    return@Button
                                }
                                if (state.teamNames.filter { team -> team.isNotEmpty() }.size < 2) {
                                    insufficientTeams = true
                                    return@Button
                                }

                                if (state.isEditModeActive) {
                                    val seriesToStore = Series(
                                        id = state.seriesId,
                                        name = state.seriesName.trim(),
                                        teams = state.teamNames.filter { team ->
                                            team.trim().isNotEmpty()
                                        }.map { team -> team.trim() },
                                        createdAt = state.createdAt.toLongOrNull(),
                                        roundRobinTimes = state.roundRobinCount.toIntOrNull()
                                            ?: 1,
                                        hidden = false
                                    )
                                    onCreateUpdateSeriesClicked(seriesToStore, true)
                                } else {
                                    val seriesToStore = Series(
                                        name = state.seriesName.trim(),
                                        teams = state.teamNames.filter { team ->
                                            team.trim().isNotEmpty()
                                        }.map { team -> team.trim() },
                                        roundRobinTimes = state.roundRobinCount.toIntOrNull()
                                            ?: 1,
                                        hidden = false
                                    )
                                    onCreateUpdateSeriesClicked(seriesToStore, false)
                                }
                            }
                        ) {
                            Text(if (state.isEditModeActive) "Update Series" else "Create Series")
                        }
                        Spacer(Modifier.width(8.dp))
                    }
                }
            }
        }
    }
}