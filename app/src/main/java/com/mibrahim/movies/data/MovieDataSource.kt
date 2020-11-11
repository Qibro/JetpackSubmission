package com.mibrahim.movies.data

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.mibrahim.movies.data.source.local.entity.MovieEntity
import com.mibrahim.movies.vo.Resource

interface MovieDataSource {
    fun getAllMovies(): LiveData<Resource<PagedList<MovieEntity>>>

    fun getMovie(movieId: String): LiveData<Resource<MovieEntity>>

    fun getAllTVShows(): LiveData<Resource<PagedList<MovieEntity>>>

    fun getTVShow(tvShowId: String): LiveData<Resource<MovieEntity>>

    fun getFavoriteMovies(): LiveData<PagedList<MovieEntity>>

    fun getFavoriteTVShows(): LiveData<PagedList<MovieEntity>>

    fun setFavoriteStatus(entity: MovieEntity,status:Boolean)

}