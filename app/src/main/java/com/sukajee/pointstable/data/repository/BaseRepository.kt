package com.sukajee.pointstable.data.repository

import com.sukajee.pointstable.data.model.Series
import kotlinx.coroutines.flow.Flow

interface BaseRepository {
    fun getMatches(): Flow<List<Series>>
    suspend fun getMatchById(matchId: Int): Series
    suspend fun insertMatch(series: Series)
    suspend fun updateMatch(series: Series)
    suspend fun deleteMatch(matchId: Int)
}