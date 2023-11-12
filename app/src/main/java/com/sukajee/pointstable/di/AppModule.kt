package com.sukajee.pointstable.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.sukajee.pointstable.data.db.PointsTableDatabase
import com.sukajee.pointstable.data.db.PointsTableDao
import com.sukajee.pointstable.data.repository.BaseRepository
import com.sukajee.pointstable.data.repository.PointsTableRepository
import com.sukajee.pointstable.utils.SHARED_PREFS
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideContext(app: Application): Context = app.applicationContext

    @Provides
    @Singleton
    fun provideAppDatabase(context: Context): PointsTableDatabase =
        Room.databaseBuilder(
            context,
            PointsTableDatabase::class.java,
            "points_table-database"
        ).build()

    @Provides
    @Singleton
    fun provideDao(database: PointsTableDatabase): PointsTableDao = database.dao()

    @Provides
    @Singleton
    fun provideWordRepository(context: Context, dao: PointsTableDao): BaseRepository =
        PointsTableRepository(context, dao)

    @Provides
    @Singleton
    fun provideSharedPreferences(context: Context): SharedPreferences =
        context.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE)
}