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
    val teamARuns: String = "",
    val teamAOvers: String = "",
    val teamABalls: String = "",
    val teamBRuns: String = "",
    val teamBOvers: String = "",
    val teamBBalls: String = ""
)