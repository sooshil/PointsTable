package com.sukajee.pointstable.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sukajee.pointstable.utils.NumberOfTeams
import com.sukajee.pointstable.utils.capitalizeWords
import com.sukajee.pointstable.utils.getNumberOfMatches
import java.util.Locale

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
) {
    val teamCount: NumberOfTeams
        get() = teams.size

    val numberOfGames: Int
        get() = teamCount.getNumberOfMatches(roundRobinTimes)

    val improvedTeams
        get() = teams.map {
            it.capitalizeWords()
        }

}
