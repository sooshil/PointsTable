package com.sukajee.pointstable.data.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [], version = 1, exportSchema = false)
abstract class PointsTableDatabase: RoomDatabase() {
    abstract fun dao(): PointsTableDao
}