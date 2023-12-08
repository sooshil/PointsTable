package com.sukajee.pointstable.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
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
            durationMillis = 500
        )
    )

    val cardColor by animateColorAsState(
        targetValue = if (expanded) Color.Transparent else MaterialTheme.colorScheme.surfaceVariant,
        animationSpec = tween(
            durationMillis = 600
        ),
        label = ""
    )

    val cardBorderColor by animateColorAsState(
        targetValue = if (expanded) MaterialTheme.colorScheme.primary else Color.Transparent,
        animationSpec = tween(
            durationMillis = 600
        ),
        label = ""
    )

    val cardHeaderColor by animateColorAsState(
        targetValue = if (expanded) MaterialTheme.colorScheme.surfaceTint.copy(alpha = 0.3f) else MaterialTheme.colorScheme.surfaceVariant,
        animationSpec = tween(
            durationMillis = 600
        ),
        label = ""
    )

    Card(
        modifier = modifier
            .clickable {
                onExpandClick()
            }
            .border(
                width = 1.dp,
                color = cardBorderColor,
                shape = MaterialTheme.shapes.medium
            ),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(
            containerColor = cardColor
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = cardHeaderColor
                    )
                    .padding(horizontal = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = game.name,
                    modifier = Modifier.weight(1f),
                    overflow = TextOverflow.Ellipsis,
                )
                Row(
                    modifier = Modifier,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    if (game.isCompleted) {
                        Icon(
                            imageVector = Icons.Filled.CheckCircle,
                            tint = MaterialTheme.colorScheme.onPrimaryContainer,
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
            AnimatedVisibility(
                visible = expanded,
                enter = expandVertically(
                    animationSpec = tween(
                        durationMillis = 500,
                        easing = LinearOutSlowInEasing
                    )
                ),
                exit = shrinkVertically(
                    animationSpec = tween(
                        durationMillis = 500,
                        easing = LinearOutSlowInEasing
                    )
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(10.dp)
                ) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = game.name.getFirstTeam(),
                        fontWeight = FontWeight.Bold
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        OutlinedTextField(
                            modifier = Modifier.weight(2f),
                            value = game.scoreData.teamARuns,
                            onValueChange = {
                                val updatedScoreData = game.scoreData.copy(
                                    teamARuns = it.take(3)
                                )
                                onUpdateGame(
                                    game.copy(
                                        scoreData = updatedScoreData
                                    )
                                )
                            },
                            label = {
                                Text(text = "Runs")
                            },
                            keyboardOptions = KeyboardOptions.Default.copy(
                                keyboardType = KeyboardType.NumberPassword,
                                imeAction = ImeAction.Next
                            ),
                            visualTransformation = VisualTransformation.None,
                            maxLines = 1
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        OutlinedTextField(
                            modifier = Modifier.weight(2f),
                            value = game.scoreData.teamAOvers,
                            onValueChange = {
                                val updatedScoreData = game.scoreData.copy(
                                    teamAOvers = it.take(2)
                                )
                                onUpdateGame(
                                    game.copy(
                                        scoreData = updatedScoreData
                                    )
                                )
                            },
                            label = {
                                Text(text = "Overs")
                            },
                            keyboardOptions = KeyboardOptions.Default.copy(
                                keyboardType = KeyboardType.NumberPassword,
                                imeAction = ImeAction.Next
                            ),
                            visualTransformation = VisualTransformation.None,
                            maxLines = 1
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        OutlinedTextField(
                            modifier = Modifier.weight(1f),
                            value = game.scoreData.teamABalls,
                            onValueChange = {
                                val updatedScoreData = game.scoreData.copy(
                                    teamABalls = if((it.take(1).toIntOrNull() ?: 0) < 6) it.take(1) else "0"
                                )
                                onUpdateGame(
                                    game.copy(
                                        scoreData = updatedScoreData
                                    )
                                )
                            },
                            label = {
                                Text(text = "Balls")
                            },
                            keyboardOptions = KeyboardOptions.Default.copy(
                                keyboardType = KeyboardType.NumberPassword,
                                imeAction = ImeAction.Next
                            ),
                            visualTransformation = VisualTransformation.None,
                            maxLines = 1
                        )
                    }
                    Spacer(modifier = Modifier.height(24.dp))
                    Text(
                        text = game.name.getSecondTeam(),
                        fontWeight = FontWeight.Bold
                    )
                    Row(Modifier.fillMaxWidth()) {
                        OutlinedTextField(
                            modifier = Modifier.weight(2f),
                            value = game.scoreData.teamBRuns,
                            onValueChange = {
                                val updatedScoreData = game.scoreData.copy(
                                    teamBRuns = it.take(3)
                                )
                                onUpdateGame(
                                    game.copy(
                                        scoreData = updatedScoreData
                                    )
                                )
                            },
                            label = {
                                Text(text = "Runs")
                            },
                            keyboardOptions = KeyboardOptions.Default.copy(
                                keyboardType = KeyboardType.NumberPassword,
                                imeAction = ImeAction.Next
                            ),
                            visualTransformation = VisualTransformation.None,
                            maxLines = 1
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        OutlinedTextField(
                            modifier = Modifier.weight(2f),
                            value = game.scoreData.teamBOvers,
                            onValueChange = {
                                val updatedScoreData = game.scoreData.copy(
                                    teamBOvers = it.take(2)
                                )
                                onUpdateGame(
                                    game.copy(
                                        scoreData = updatedScoreData
                                    )
                                )
                            },
                            label = {
                                Text(text = "Overs")
                            },
                            keyboardOptions = KeyboardOptions.Default.copy(
                                keyboardType = KeyboardType.NumberPassword,
                                imeAction = ImeAction.Next
                            ),
                            visualTransformation = VisualTransformation.None,
                            maxLines = 1
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        OutlinedTextField(
                            modifier = Modifier.weight(1f),
                            value = game.scoreData.teamBBalls,
                            onValueChange = {
                                val updatedScoreData = game.scoreData.copy(
                                    teamBBalls = if((it.take(1).toIntOrNull() ?: 0) < 6) it.take(1) else "0"
                                )
                                onUpdateGame(
                                    game.copy(
                                        scoreData = updatedScoreData
                                    )
                                )
                            },
                            label = {
                                Text(text = "Balls")
                            },
                            keyboardOptions = KeyboardOptions.Default.copy(
                                keyboardType = KeyboardType.NumberPassword,
                                imeAction = ImeAction.Next
                            ),
                            visualTransformation = VisualTransformation.None,
                            maxLines = 1
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceEvenly
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
                }
            }
        }
    }
}