package com.sukajee.pointstable.data.model

data class Game(
    val id: Int = 0,
    val seriesId: Int,
    val name: String,
    val scoreData: ScoreData,
    val isNoResult: Boolean = false,
    val isTied: Boolean = false
) {
    val teamAWon
        get() = (scoreData.teamARuns.toIntOrNull() ?: 0) > (scoreData.teamBRuns.toIntOrNull() ?: 0)

    private val teamATotalBalls
        get() = (scoreData.teamAOvers.toIntOrNull() ?: 0) * 6 + (scoreData.teamABalls.toIntOrNull()
            ?: 0)

    private val teamBTotalBalls
        get() = (scoreData.teamBOvers.toIntOrNull() ?: 0) * 6 + (scoreData.teamBBalls.toIntOrNull()
            ?: 0)

    val isCompleted
        get() = ((scoreData.teamARuns.toIntOrNull() ?: 0) > 0 &&
                teamATotalBalls > 0 &&
                (scoreData.teamBRuns.toIntOrNull() ?: 0) > 0 &&
                teamBTotalBalls > 0) || isTied || isNoResult
}

data class ScoreData(
    val teamARuns: String = "",
    val teamAOvers: String = "",
    val teamABalls: String = "",
    val teamBRuns: String = "",
    val teamBOvers: String = "",
    val teamBBalls: String = ""
)