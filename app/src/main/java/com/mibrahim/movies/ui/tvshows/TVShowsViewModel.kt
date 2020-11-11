package com.mibrahim.movies.ui.tvshows

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.mibrahim.movies.data.MovieRepository

import com.mibrahim.movies.data.source.local.entity.MovieEntity
import com.mibrahim.movies.vo.Resource

class TVShowsViewModel(private val tvShowRepository: MovieRepository) : ViewModel() {

    fun getTVShows(): LiveData<Resource<PagedList<MovieEntity>>> = tvShowRepository.getAllTVShows()
}

