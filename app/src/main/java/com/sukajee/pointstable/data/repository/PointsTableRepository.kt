package com.sukajee.pointstable.data.repository

import android.content.Context
import com.sukajee.pointstable.data.db.PointsTableDao
import com.sukajee.pointstable.data.model.Series
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PointsTableRepository @Inject constructor(
    private val context: Context,
    private val dao: PointsTableDao
): BaseRepository {
    override fun getMatches(): Flow<List<Series>> = dao.getAllSeries()

    override suspend fun getMatchById(matchId: Int): Series = dao.getSeriesById(matchId)

    override suspend fun insertMatch(series: Series) = dao.insertSeriesWithTimeStamp(series)

    override suspend fun updateMatch(series: Series) = dao.updateSeriesWithTimeStamp(series)

    override suspend fun deleteMatch(matchId: Int) = dao.deleteSeries(matchId)

}