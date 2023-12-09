package com.sukajee.pointstable.data.model

import com.sukajee.pointstable.utils.getFirstTeam
import com.sukajee.pointstable.utils.getSecondTeam

fun Game.toGameSaveable() = GameSaveable(
    id = id,
    seriesId = seriesId,
    name = name,
    firstTeamName = name.getFirstTeam(),
    secondTeamName = name.getSecondTeam(),
    isNoResult = isNoResult,
    isTied = isTied,
    teamARuns = scoreData.teamARuns,
    teamAOvers = scoreData.teamAOvers,
    teamABalls = scoreData.teamABalls,
    teamBRuns = scoreData.teamBRuns,
    teamBOvers = scoreData.teamBOvers,
    teamBBalls = scoreData.teamBBalls,
    isCompleted = isCompleted
)

fun GameSaveable.toGame() = Game(
    id = id,
    seriesId = seriesId,
    name = name,
    scoreData = ScoreData(
        teamARuns = teamARuns,
        teamAOvers = teamAOvers,
        teamABalls = teamABalls,
        teamBRuns = teamBRuns,
        teamBOvers = teamBOvers,
        teamBBalls = teamBBalls
    ),
    isNoResult = isNoResult,
    isTied = isTied
)