package com.sukajee.pointstable.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sukajee.pointstable.data.model.Match

@Database(entities = [Match::class], version = 1, exportSchema = false)
abstract class PointsTableDatabase: RoomDatabase() {
    abstract fun dao(): PointsTableDao
}