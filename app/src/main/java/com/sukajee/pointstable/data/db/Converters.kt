package com.sukajee.pointstable.data.db

import androidx.room.TypeConverter

class Converters {

    @TypeConverter
    fun stringListToString(teamIds: List<String>): String = teamIds.joinToString(separator = ",")

    @TypeConverter
    fun stringToStringList(idString: String): List<String> = idString.split(",").map { it }
}