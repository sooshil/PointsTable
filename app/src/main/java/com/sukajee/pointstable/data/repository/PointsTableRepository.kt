package com.sukajee.pointstable.data.repository

import android.content.Context
import com.sukajee.pointstable.data.db.PointsTableDao
import javax.inject.Inject

class PointsTableRepository @Inject constructor(
    private val context: Context,
    private val dao: PointsTableDao
): BaseRepository {

}