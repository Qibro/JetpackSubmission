package com.mibrahim.movies.ui.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.mibrahim.movies.data.MovieRepository

import com.mibrahim.movies.data.source.local.entity.MovieEntity
import com.mibrahim.movies.vo.Resource

class MovieViewModel(private val movieRepository: MovieRepository) : ViewModel() {

    fun getMovies(): LiveData<Resource<PagedList<MovieEntity>>> = movieRepository.getAllMovies()
}

