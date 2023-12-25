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