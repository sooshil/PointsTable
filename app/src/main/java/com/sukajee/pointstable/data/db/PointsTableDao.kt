package com.sukajee.pointstable.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.sukajee.pointstable.data.model.Match
import kotlinx.coroutines.flow.Flow

@Dao
interface PointsTableDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMatch(match: Match)

    suspend fun insertMatchWithTimeStamp(match: Match) {
        val resultMatch = match.copy(
            createdAt = System.currentTimeMillis(),
            updatedAt = System.currentTimeMillis()
        )
        insertMatch(resultMatch)
    }

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateMatch(match: Match)

    suspend fun updateMatchWithTimeStamp(match: Match) {
        val resultMatch = match.copy(
            updatedAt = System.currentTimeMillis()
        )
        updateMatch(resultMatch)
    }

    @Query("DELETE FROM matches WHERE id = :matchId")
    suspend fun deleteMatch(matchId: Int)

    @Query(value = "SELECT * FROM matches WHERE id = :matchId")
    suspend fun getMatchById(matchId: Int): Match

    @Query(value = "SELECT * FROM matches WHERE hidden = 0 ORDER BY created_at DESC")
    fun getAllMatches(): Flow<Match>
}