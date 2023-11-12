package com.sukajee.pointstable.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "matches")
data class Match(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    @ColumnInfo(name = "start_date")
    val startDate: String,
    val venue: String,
    @ColumnInfo(name = "number_of_teams")
    val numberOfTeams: Int,
    val completed: Boolean,
    val hidden: Boolean
)
