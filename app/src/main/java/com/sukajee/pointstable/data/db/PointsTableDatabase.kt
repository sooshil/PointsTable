package com.sukajee.pointstable.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.sukajee.pointstable.data.model.GameSaveable
import com.sukajee.pointstable.data.model.Series

@Database(entities = [Series::class, GameSaveable::class], version = 1, exportSchema = false)
@TypeConverters (Converters::class)
abstract class PointsTableDatabase: RoomDatabase() {
    abstract fun dao(): PointsTableDao
}