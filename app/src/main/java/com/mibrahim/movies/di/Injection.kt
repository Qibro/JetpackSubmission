package com.mibrahim.movies.di

import android.content.Context
import com.mibrahim.movies.data.MovieRepository
import com.mibrahim.movies.data.source.local.LocalDataSource
import com.mibrahim.movies.data.source.local.room.MovieDatabase
import com.mibrahim.movies.data.source.remote.RemoteDataSource
import com.mibrahim.movies.utils.AppExecutors
import com.mibrahim.movies.utils.JsonHelper

object Injection {
    fun provideRepostiory(context: Context): MovieRepository {

        val database = MovieDatabase.getInstance(context)

        val remoteDataSource = RemoteDataSource.getInstance(JsonHelper(context))
        val localDataSource = LocalDataSource.getInstance(database.movieDao())
        val appExecutor = AppExecutors()
        return MovieRepository.getInstance(remoteDataSource,localDataSource,appExecutor)
    }
}