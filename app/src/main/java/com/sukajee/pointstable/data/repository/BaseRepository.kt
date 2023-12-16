package com.sukajee.pointstable.data.repository

import com.sukajee.pointstable.data.model.Game
import com.sukajee.pointstable.data.model.Series
import kotlinx.coroutines.flow.Flow

interface BaseRepository {
    fun getSeries(): Flow<List<Series>>
    suspend fun getSeriesById(seriesId: Int): Series?
    suspend fun insertSeries(series: Series)
    suspend fun updateSeries(series: Series)
    suspend fun deleteSeries(seriesId: Int)

    suspend fun insertGame(game: Game)
    suspend fun updateGame(game: Game)
    suspend fun getGamesBySeriesId(seriesId: Int): List<Game>
}