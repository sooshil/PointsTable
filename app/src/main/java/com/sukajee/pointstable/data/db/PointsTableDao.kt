/*
 * Copyright (c) 2023, Sushil Kafle. All rights reserved.
 *
 * This file is part of the Android project authored by Sushil Kafle.
 * Unauthorized copying and using of this file, via any medium, is strictly prohibited.
 * Proprietary and confidential.
 * For inquiries, please contact: info@sukajee.com
 * Last modified by Sushil on Sunday, 24 Dec, 2023.
 */

package com.sukajee.pointstable.data.db

import android.util.Log
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.sukajee.pointstable.data.model.Game
import com.sukajee.pointstable.data.model.Series
import kotlinx.coroutines.flow.Flow

@Dao
interface PointsTableDao {

    /**
     * Series DAO Operations
     * */

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSeries(series: Series)

    suspend fun insertSeriesWithTimeStamp(series: Series) {
        val resultSeries = series.createdAt?.let {
            series
        } ?: series.copy(
            createdAt = System.currentTimeMillis(),
            updatedAt = System.currentTimeMillis()
        )
        insertSeries(resultSeries)
    }

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateSeries(series: Series)

    suspend fun updateSeriesWithTimeStamp(series: Series) {
        val resultSeries = series.copy(
            updatedAt = System.currentTimeMillis()
        )
        Log.d("DAO", "series being updated: $resultSeries")
        updateSeries(resultSeries)
    }

    @Query("DELETE FROM series WHERE id = :seriesId")
    suspend fun deleteSeries(seriesId: Int)

    @Query(value = "SELECT * FROM series WHERE id = :seriesId")
    suspend fun getSeriesById(seriesId: Int): Series?

    @Query(value = "SELECT * FROM series WHERE hidden = 0 ORDER BY created_at DESC")
    fun getAllSeries(): Flow<List<Series>>


    /**
     * Games DAO Operations
     * */

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGame(game: Game)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateGame(game: Game)

    @Query("DELETE FROM games WHERE id = :gameId")
    suspend fun deleteGame(gameId: Int)

    @Query(value = "SELECT * FROM games WHERE seriesId = :seriesId")
    suspend fun getGamesBySeriesId(seriesId: Int): List<Game>
}