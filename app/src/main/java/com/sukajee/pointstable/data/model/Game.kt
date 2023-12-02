package com.sukajee.pointstable.data.model

import com.sukajee.pointstable.ui.components.ScoreData

data class Game(
    val id: Int = 0,
    val seriesId: Int = 0,
    val name: String,
    val scoreData: ScoreData,
    val isNoResult: Boolean = false,
    val isAbandoned: Boolean = false
) {
    val teamAWon
        get() = scoreData.teamARuns > scoreData.teamBRuns

    private val teamATotalBalls
        get() = scoreData.teamAOvers * 6 + scoreData.teamABalls

    private val teamBTotalBalls
        get() = scoreData.teamBOvers * 6 + scoreData.teamBBalls

    val isCompleted
        get() = (scoreData.teamARuns > 0 &&
                teamATotalBalls > 0 &&
                scoreData.teamBRuns > 0 &&
                teamBTotalBalls > 0) || isAbandoned || isNoResult
}
