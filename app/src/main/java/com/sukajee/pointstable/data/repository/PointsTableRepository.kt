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
    override fun getSeries(): Flow<List<Series>> = dao.getAllSeries()

    override suspend fun getSeriesById(seriesId: Int): Series = dao.getSeriesById(seriesId)

    override suspend fun insertSeries(series: Series) = dao.insertSeriesWithTimeStamp(series)

    override suspend fun updateSeries(series: Series) = dao.updateSeriesWithTimeStamp(series)

    override suspend fun deleteSeries(seriesId: Int) = dao.deleteSeries(seriesId)

}