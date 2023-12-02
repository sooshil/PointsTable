@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

package com.sukajee.pointstable.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sukajee.pointstable.data.model.Series

@Composable
fun AddEditSeriesBottomSheet(
    inEditMode: Boolean = false,
    series: Series? = null,
    updateBottomSheetVisibility: (shouldShow: Boolean) -> Unit,
    onCreateUpdateSeriesClicked: (series: Series) -> Unit,
    onCancelButtonClicked: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var seriesName by rememberSaveable {
        if (inEditMode) {
            mutableStateOf(series?.name ?: "")
        } else {
            mutableStateOf("")
        }
    }
    var roundRobinTimes by rememberSaveable {
        if (inEditMode) {
            mutableStateOf(series?.roundRobinTimes.toString())
        } else {
            mutableStateOf("")
        }
    }

    var teams by rememberSaveable {
        if (inEditMode) {
            mutableStateOf(series?.improvedTeams ?: emptyList())
        } else {
            mutableStateOf(emptyList())
        }
    }
    var isErrorInSeriesName by rememberSaveable { mutableStateOf(false) }
    var insufficientTeams by rememberSaveable { mutableStateOf(false) }

    ModalBottomSheet(
        sheetState = sheetState,
        onDismissRequest = {
            updateBottomSheetVisibility(false)
        },
        dragHandle = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                BottomSheetDefaults.DragHandle()
                Text(
                    text = if (inEditMode) "Edit Series" else "Create a Series",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(Modifier.height(8.dp))
                Divider()
            }
        }
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            LazyColumn(
                modifier = Modifier
                    .padding(horizontal = 8.dp)
            ) {
                item {
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        modifier = Modifier
                            .fillMaxWidth(),
                        value = seriesName,
                        onValueChange = {
                            seriesName = it
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
                        value = roundRobinTimes,
                        onValueChange = {
                            roundRobinTimes = it.trim()
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
                items(teams.size) { index ->
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        modifier = Modifier
                            .fillMaxWidth(),
                        value = teams[index],
                        onValueChange = { team ->
                            val existingTeams = teams.toMutableList()
                            existingTeams[index] = team
                            teams = existingTeams.toList()
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
                            val existingTeams = teams.toMutableList()
                            existingTeams.add("")
                            teams = existingTeams.toList()
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
                            onClick = {
                                onCancelButtonClicked()
                            }
                        ) {
                            Text("Cancel")
                        }
                        Spacer(Modifier.width(8.dp))
                        Button(
                            modifier = Modifier.weight(1f),
                            onClick = {
                                if (seriesName.isEmpty()) {
                                    isErrorInSeriesName = true
                                    return@Button
                                }
                                if (teams.filter { it.isNotEmpty() }.size < 2) {
                                    insufficientTeams = true
                                    return@Button
                                }
                                val seriesToStore = if (inEditMode && series != null) {
                                    series.copy(
                                        name = seriesName.trim(),
                                        teams = teams.filter { it.trim().isNotEmpty() }
                                            .map { it.trim() },
                                        roundRobinTimes = roundRobinTimes.toIntOrNull() ?: 1,
                                        hidden = false
                                    )
                                } else {
                                    Series(
                                        name = seriesName.trim(),
                                        teams = teams.filter { it.trim().isNotEmpty() }
                                            .map { it.trim() },
                                        roundRobinTimes = roundRobinTimes.toIntOrNull() ?: 1,
                                        hidden = false
                                    )
                                }
                                onCreateUpdateSeriesClicked(seriesToStore)
                            }
                        ) {
                            Text(if (inEditMode) "Update Series" else "Create Series")
                        }
                        Spacer(Modifier.width(8.dp))
                    }
                }
            }
        }
    }
}