package com.sukajee.pointstable.data.repository

import android.content.Context
import com.sukajee.pointstable.data.db.PointsTableDao
import com.sukajee.pointstable.data.model.Match
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PointsTableRepository @Inject constructor(
    private val context: Context,
    private val dao: PointsTableDao
): BaseRepository {
    override fun getMatches(): Flow<List<Match>> = dao.getAllMatches()

    override suspend fun getMatchById(matchId: Int): Match = dao.getMatchById(matchId)

    override suspend fun insertMatch(match: Match) = dao.insertMatchWithTimeStamp(match)

    override suspend fun updateMatch(match: Match) = dao.updateMatchWithTimeStamp(match)

    override suspend fun deleteMatch(matchId: Int) = dao.deleteMatch(matchId)

}