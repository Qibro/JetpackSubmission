package com.mibrahim.movies.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.mibrahim.movies.data.MovieRepository
import com.mibrahim.movies.data.source.local.entity.MovieEntity
import com.mibrahim.movies.vo.Resource

class DetailViewModel(private val movieRepository: MovieRepository) : ViewModel() {
    val movieId = MutableLiveData<String>()
    val type = MutableLiveData<Int>()
    fun setSelectedMovie(movieId: String) {
        this.movieId.value = movieId
    }
    fun setType(type: Int) {
        this.type.value = type
    }

    val entity: LiveData<Resource<MovieEntity>> = Transformations.switchMap(movieId) { data ->
        when (type.value) {
            1 -> movieRepository.getMovie(data)
            2 -> movieRepository.getTVShow(data)
            else -> null
        }
    }

    fun setFavorite(){
        if(entity.value != null){
            val favorite = entity.value!!.data as MovieEntity
            movieRepository.setFavoriteStatus(favorite, true)
        }
    }

    fun removeFavorite(){
        if(entity.value != null){
            val favorite = entity.value!!.data as MovieEntity
            movieRepository.setFavoriteStatus(favorite, false)
        }
    }


}


