package com.sukajee.pointstable.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sukajee.pointstable.utils.NumberOfTeams
import com.sukajee.pointstable.utils.getNumberOfMatches

@Entity(tableName = "series")
data class Series(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    @ColumnInfo(name = "start_date")
    val startDate: String,
    val teamIds: List<Int>,
    val roundRobinTimes: Int = 1,
    val completed: Boolean,
    val hidden: Boolean,
    @ColumnInfo(name = "created_at")
    val createdAt: Long? = null,
    @ColumnInfo(name = "updated_at")
    val updatedAt: Long? = null
) {
    val teamCount: NumberOfTeams
        get() = teamIds.size

    val numberOfGames: Int
        get() = teamCount.getNumberOfMatches(roundRobinTimes)
}