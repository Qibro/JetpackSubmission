package com.mibrahim.movies.ui.favorite.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.mibrahim.movies.data.MovieRepository
import com.mibrahim.movies.data.source.local.entity.MovieEntity

class FavoriteMovieViewModel(private val movieRepository: MovieRepository) : ViewModel() {

    fun getMovies(): LiveData<PagedList<MovieEntity>> = movieRepository.getFavoriteMovies()

    fun removeFavorite(entity: MovieEntity){
        val status = !entity.status
        movieRepository.setFavoriteStatus(entity,status)
    }
}

