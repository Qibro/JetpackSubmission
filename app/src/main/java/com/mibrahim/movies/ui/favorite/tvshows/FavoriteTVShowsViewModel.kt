package com.mibrahim.movies.ui.favorite.tvshows

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.mibrahim.movies.data.MovieRepository
import com.mibrahim.movies.data.source.local.entity.MovieEntity

class FavoriteTVShowsViewModel(private val tvShowRepository: MovieRepository) : ViewModel() {

    fun getTVShows(): LiveData<PagedList<MovieEntity>> = tvShowRepository.getFavoriteTVShows()

    fun removeFavorite(entity: MovieEntity) {
        val status = !entity.status
        tvShowRepository.setFavoriteStatus(entity, status)
    }
}

