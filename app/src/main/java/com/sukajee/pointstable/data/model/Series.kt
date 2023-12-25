/*
 * Copyright (c) 2023, Sushil Kafle. All rights reserved.
 *
 * This file is part of the Android project authored by Sushil Kafle.
 * Unauthorized copying and using of this file, via any medium, is strictly prohibited.
 * Proprietary and confidential.
 * For inquiries, please contact: info@sukajee.com
 * Last modified by Sushil on Sunday, 24 Dec, 2023.
 */

package com.sukajee.pointstable.data.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sukajee.pointstable.utils.NumberOfTeams
import com.sukajee.pointstable.utils.capitalizeFirstLetter
import com.sukajee.pointstable.utils.capitalizeWords
import com.sukajee.pointstable.utils.getNumberOfMatches
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "series")
data class Series(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val teams: List<String>,
    val roundRobinTimes: Int = 1,
    val hidden: Boolean,
    @ColumnInfo(name = "created_at")
    val createdAt: Long? = null,
    @ColumnInfo(name = "updated_at")
    val updatedAt: Long? = null
) : Parcelable {
    val teamCount: NumberOfTeams
        get() = teams.size

    val numberOfGames: Int
        get() = teamCount.getNumberOfMatches(roundRobinTimes)

    val improvedTeams: List<String>
        get() = teams.map {
            if (it.trim().contains(" ").not()) it.capitalizeFirstLetter()
            else it.capitalizeWords()
        }

    val seriesName: String
        get() = if (name.trim().contains(" ").not()) name.capitalizeFirstLetter()
        else name.capitalizeWords()
}
