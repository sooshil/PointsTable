package com.sukajee.pointstable.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sukajee.pointstable.data.model.Match
import kotlinx.coroutines.flow.Flow

@Dao
interface PointsTableDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMatch(match: Match)

    @Delete
    suspend fun deleteMatch(match: Match)

    @Query(value = "SELECT * FROM matches WHERE id = :matchId")
    suspend fun getMatchById(matchId: Int): Flow<Match>

    @Query(value = "SELECT * FROM matches WHERE hidden = 0")
    suspend fun getAllMatches(matchId: Int): Flow<Match>

}