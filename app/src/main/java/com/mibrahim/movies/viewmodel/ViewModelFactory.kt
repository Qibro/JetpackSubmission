package com.mibrahim.movies.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mibrahim.movies.data.MovieRepository
import com.mibrahim.movies.di.Injection
import com.mibrahim.movies.ui.detail.DetailViewModel
import com.mibrahim.movies.ui.favorite.movie.FavoriteMovieViewModel
import com.mibrahim.movies.ui.movie.MovieViewModel
import com.mibrahim.movies.ui.favorite.tvshows.FavoriteTVShowsViewModel
import com.mibrahim.movies.ui.tvshows.TVShowsViewModel

class ViewModelFactory private constructor(private val mMovieRepository: MovieRepository) :
    ViewModelProvider.NewInstanceFactory() {
    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null

        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(Injection.provideRepostiory(context))
            }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        when {
            modelClass.isAssignableFrom(MovieViewModel::class.java) -> {
                return MovieViewModel(mMovieRepository) as T
            }
            modelClass.isAssignableFrom(TVShowsViewModel::class.java) -> {
                return TVShowsViewModel(mMovieRepository) as T
            }
            modelClass.isAssignableFrom(DetailViewModel::class.java) -> {
                return DetailViewModel(mMovieRepository) as T
            }
            modelClass.isAssignableFrom(FavoriteMovieViewModel::class.java) -> {
                return FavoriteMovieViewModel(mMovieRepository) as T
            }
            modelClass.isAssignableFrom(FavoriteTVShowsViewModel::class.java) -> {
                return FavoriteTVShowsViewModel(mMovieRepository) as T
            }
            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }

    }
}