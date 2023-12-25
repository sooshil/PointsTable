/*
 * Copyright (c) 2023, Sushil Kafle. All rights reserved.
 *
 * This file is part of the Android project authored by Sushil Kafle.
 * Unauthorized copying and using of a part or entirety of the code in this file, via any medium, is strictly prohibited.
 * Proprietary and confidential.
 * For inquiries, please contact: info@sukajee.com
 * Last modified by Sushil on Sunday, 24 Dec, 2023.
 */

package com.sukajee.pointstable.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "games")
data class Game(
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
) {
    val teamAWon
        get() = (teamARuns.toIntOrNull() ?: 0) > (teamBRuns.toIntOrNull() ?: 0)

    private val teamATotalBalls
        get() = (teamAOvers.toIntOrNull() ?: 0) * 6 + (teamABalls.toIntOrNull()
            ?: 0)

    private val teamBTotalBalls
        get() = (teamBOvers.toIntOrNull() ?: 0) * 6 + (teamBBalls.toIntOrNull()
            ?: 0)

    val isEntryCompleted: Boolean
        get() = ((teamARuns.toIntOrNull() ?: 0) > 0 &&
                teamATotalBalls > 0 &&
                (teamBRuns.toIntOrNull() ?: 0) > 0 &&
                teamBTotalBalls > 0) || isTied || isNoResult
}