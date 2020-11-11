package com.mibrahim.movies.data.source.local.room

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import com.mibrahim.movies.data.source.local.entity.MovieEntity

@Dao
interface MovieDao {
    @Query("SELECT * FROM movieEntities where type = 1")
    fun getMovies(): DataSource.Factory<Int,MovieEntity>

    @Query("SELECT * FROM movieEntities where type = 1 AND id=:id")
    fun getMovieById(id: String): LiveData<MovieEntity>

    @Query("SELECT * FROM movieEntities where type = 2")
    fun getTVShows(): DataSource.Factory<Int,MovieEntity>

    @Query("SELECT * FROM movieEntities where type = 2 AND id=:id")
    fun getTVShowsById(id: String): LiveData<MovieEntity>

    @Query("SELECT * FROM movieEntities WHERE type = 1 AND status = 1")
    fun getFavoriteMovies(): DataSource.Factory<Int,MovieEntity>

    @Query("SELECT * FROM movieEntities WHERE type = 2 AND status = 1")
    fun getFavoriteTvShows(): DataSource.Factory<Int,MovieEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertEntity(entity: List<MovieEntity>)

    @Update
    fun updateEntity(entity: MovieEntity)

}
