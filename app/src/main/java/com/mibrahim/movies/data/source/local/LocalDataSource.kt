package com.mibrahim.movies.data.source.local

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import com.mibrahim.movies.data.source.local.entity.MovieEntity
import com.mibrahim.movies.data.source.local.room.MovieDao

class LocalDataSource private constructor(private val mMovieDao: MovieDao) {
    companion object {
        private var INSTANCE: LocalDataSource? = null

        fun getInstance(movieDao: MovieDao): LocalDataSource =
            INSTANCE ?: LocalDataSource(movieDao)
    }

    fun getAllMovies(): DataSource.Factory<Int, MovieEntity> = mMovieDao.getMovies()

    fun getMovieById(id: String): LiveData<MovieEntity> = mMovieDao.getMovieById(id)

    fun getAllTVShows(): DataSource.Factory<Int, MovieEntity> = mMovieDao.getTVShows()

    fun getTVShowById(id: String): LiveData<MovieEntity> = mMovieDao.getTVShowsById(id)

    fun insertEntity(movies: List<MovieEntity>) = mMovieDao.insertEntity(movies)

    fun getFavoriteMovies(): DataSource.Factory<Int, MovieEntity> = mMovieDao.getFavoriteMovies()

    fun getFavoriteTVShows(): DataSource.Factory<Int, MovieEntity> = mMovieDao.getFavoriteTvShows()

    fun updateFavoriteStatus(movie: MovieEntity,status:Boolean) {
        movie.status = status
        mMovieDao.updateEntity(movie)
    }

}