package com.sukajee.pointstable.data.db

import androidx.room.TypeConverter

class Converters {

    @TypeConverter
    fun intListToString(teamIds: List<Int>): String = teamIds.joinToString(separator = ",")

    @TypeConverter
    fun stringToIntList(idString: String): List<Int> = idString.split(",").map { it.toInt() }
}