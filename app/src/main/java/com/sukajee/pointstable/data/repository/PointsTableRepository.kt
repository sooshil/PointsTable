/*
 * Copyright (c) 2023, Sushil Kafle. All rights reserved.
 *
 * This file is part of the Android project authored by Sushil Kafle.
 * Unauthorized copying and using of this file, via any medium, is strictly prohibited.
 * Proprietary and confidential.
 * For inquiries, please contact: info@sukajee.com
 * Last modified by Sushil on Sunday, 24 Dec, 2023.
 */

package com.sukajee.pointstable.data.repository

import android.content.Context
import com.sukajee.pointstable.data.db.PointsTableDao
import com.sukajee.pointstable.data.model.Game
import com.sukajee.pointstable.data.model.Series
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PointsTableRepository @Inject constructor(
    private val context: Context,
    private val dao: PointsTableDao
) : BaseRepository {
    override fun getSeries(): Flow<List<Series>> = dao.getAllSeries()

    override suspend fun getSeriesById(seriesId: Int): Series? = dao.getSeriesById(seriesId)

    override suspend fun insertSeries(series: Series) = dao.insertSeriesWithTimeStamp(series)

    override suspend fun updateSeries(series: Series) = dao.updateSeriesWithTimeStamp(series)

    override suspend fun deleteSeries(seriesId: Int) = dao.deleteSeries(seriesId)

    override suspend fun insertGame(game: Game) = dao.insertGame(game = game)

    override suspend fun updateGame(game: Game) = dao.updateGame(game = game)

    override suspend fun getGamesBySeriesId(seriesId: Int): List<Game> =
        dao.getGamesBySeriesId(seriesId = seriesId)
}