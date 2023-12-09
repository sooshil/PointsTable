package com.sukajee.pointstable.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "games")
data class GameSaveable(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val seriesId: Int,
    val name: String,
    val firstTeamName: String,
    val secondTeamName: String,
    val isNoResult: Boolean = false,
    val isTied: Boolean = false,
    val teamARuns: String = "",
    val teamAOvers: String = "",
    val teamABalls: String = "",
    val teamBRuns: String = "",
    val teamBOvers: String = "",
    val teamBBalls: String = "",
    val isCompleted: Boolean = false
)