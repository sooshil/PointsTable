package com.sukajee.pointstable.data.repository

import com.sukajee.pointstable.data.model.Match
import kotlinx.coroutines.flow.Flow

interface BaseRepository {
    fun getMatches(): Flow<Match>
    suspend fun getMatchById(matchId: Int): Match
    suspend fun insertMatch(match: Match)
    suspend fun updateMatch(match: Match)
    suspend fun deleteMatch(matchId: Int)
}