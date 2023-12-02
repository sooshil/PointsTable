package com.sukajee.pointstable.ui.components

import androidx.compose.runtime.Composable
import com.sukajee.pointstable.data.model.Series

@Composable
fun SeriesNameRow(
    series: Series,
    isExpanded: Boolean,
    onSaveClick: () -> Unit
) {

}

data class ScoreData(
    val teamARuns: Int,
    val teamAOvers: Int,
    val teamABalls: Int,
    val teamBRuns: Int,
    val teamBOvers: Int,
    val teamBBalls: Int
)