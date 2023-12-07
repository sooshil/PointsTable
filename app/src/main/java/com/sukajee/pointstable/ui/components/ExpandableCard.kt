package com.sukajee.pointstable.ui.components

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.sukajee.pointstable.data.model.Game
import com.sukajee.pointstable.utils.getFirstTeam
import com.sukajee.pointstable.utils.getSecondTeam

@Composable
fun ExpandableCard(
    modifier: Modifier = Modifier,
    game: Game,
    onUpdateGame: (Game) -> Unit,
    onExpandClick: () -> Unit,
    expanded: Boolean,
) {
    val rotationState by animateFloatAsState(
        targetValue = if (expanded) 180f else 0f,
        label = "",
        animationSpec = tween(
            durationMillis = 700
        )
    )

    Card(
        modifier = modifier
            .clickable {
                onExpandClick()
            }
            .animateContentSize(
                animationSpec = tween(
                    durationMillis = 700,
                    easing = LinearOutSlowInEasing
                )
            ),
        shape = MaterialTheme.shapes.medium,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp)
        ) {
            Row(
                modifier = Modifier,
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = game.name,
                    modifier = Modifier.weight(1f),
                    overflow = TextOverflow.Ellipsis
                )
                Row(
                    modifier = Modifier,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    if (game.isCompleted) {
                        Icon(
                            imageVector = Icons.Filled.CheckCircle,
                            tint = Color.Green,
                            contentDescription = null
                        )
                    }
                    IconButton(
                        modifier = Modifier.rotate(rotationState),
                        onClick = onExpandClick
                    ) {
                        Icon(
                            imageVector = Icons.Filled.KeyboardArrowDown,
                            contentDescription = "Expand card to enter game data"
                        )
                    }
                }
            }
            if (expanded) {
                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Row(
                        Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Checkbox(
                                checked = game.isNoResult,
                                onCheckedChange = {
                                    onUpdateGame(
                                        game.copy(
                                            isNoResult = it
                                        )
                                    )
                                }
                            )
                            Text(text = "No Result")
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Checkbox(
                                checked = game.isTied,
                                onCheckedChange = {
                                    onUpdateGame(
                                        game.copy(
                                            isTied = it
                                        )
                                    )
                                }
                            )
                            Text(text = "Tied")
                        }
                    }
                    Row(Modifier.fillMaxWidth()) {
                        OutlinedTextField(
                            modifier = Modifier.weight(1f),
                            value = game.scoreData.teamARuns,
                            onValueChange = {
                                val updatedScoreData = game.scoreData.copy(
                                    teamARuns = it
                                )
                                onUpdateGame(
                                    game.copy(
                                        scoreData = updatedScoreData
                                    )
                                )
                            },
                            label = {
                                Text(text = "${game.name.getFirstTeam()} runs")
                            }
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        OutlinedTextField(
                            modifier = Modifier.weight(1f),
                            value = game.scoreData.teamBRuns,
                            onValueChange = {
                                val updatedScoreData = game.scoreData.copy(
                                    teamBRuns = it
                                )
                                onUpdateGame(
                                    game.copy(
                                        scoreData = updatedScoreData
                                    )
                                )
                            },
                            label = {
                                Text(text = "${game.name.getSecondTeam()} runs")
                            }
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(Modifier.fillMaxWidth()) {
                        OutlinedTextField(
                            modifier = Modifier.weight(1f),
                            value = game.scoreData.teamAOvers,
                            onValueChange = {
                                val updatedScoreData = game.scoreData.copy(
                                    teamAOvers = it
                                )
                                onUpdateGame(
                                    game.copy(
                                        scoreData = updatedScoreData
                                    )
                                )
                            },
                            label = {
                                Text(text = "${game.name.getFirstTeam()} overs")
                            }
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        OutlinedTextField(
                            modifier = Modifier.weight(1f),
                            value = game.scoreData.teamBOvers,
                            onValueChange = {
                                val updatedScoreData = game.scoreData.copy(
                                    teamBOvers = it
                                )
                                onUpdateGame(
                                    game.copy(
                                        scoreData = updatedScoreData
                                    )
                                )
                            },
                            label = {
                                Text(text = "${game.name.getSecondTeam()} overs")
                            }
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(Modifier.fillMaxWidth()) {
                        OutlinedTextField(
                            modifier = Modifier.weight(1f),
                            value = game.scoreData.teamABalls,
                            onValueChange = {
                                val updatedScoreData = game.scoreData.copy(
                                    teamABalls = it
                                )
                                onUpdateGame(
                                    game.copy(
                                        scoreData = updatedScoreData
                                    )
                                )
                            },
                            label = {
                                Text(text = "${game.name.getFirstTeam()} balls")
                            }
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        OutlinedTextField(
                            modifier = Modifier.weight(1f),
                            value = game.scoreData.teamBBalls,
                            onValueChange = {
                                val updatedScoreData = game.scoreData.copy(
                                    teamBBalls = it
                                )
                                onUpdateGame(
                                    game.copy(
                                        scoreData = updatedScoreData
                                    )
                                )
                            },
                            label = {
                                Text(text = "${game.name.getSecondTeam()} balls")
                            }
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}