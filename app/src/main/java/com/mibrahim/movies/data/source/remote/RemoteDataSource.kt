package com.mibrahim.movies.data.source.remote

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mibrahim.movies.data.source.remote.response.MovieResponse
import com.mibrahim.movies.utils.EspressoIdlingResource
import com.mibrahim.movies.utils.JsonHelper

class RemoteDataSource private constructor(private val jsonHelper: JsonHelper) {

    private val handler = Handler(Looper.getMainLooper())

    companion object {
        private const val SERVICE_LATENCY_IN_MILLIS: Long = 2000

        @Volatile
        private var instance: RemoteDataSource? = null

        fun getInstance(helper: JsonHelper): RemoteDataSource =
            instance ?: synchronized(this) {
                instance ?: RemoteDataSource(helper)
            }
    }

    fun getAllMovies(): LiveData<ApiResponse<List<MovieResponse>>> {
        EspressoIdlingResource.increment()
        val resultMovie = MutableLiveData<ApiResponse<List<MovieResponse>>>()
        handler.postDelayed(
            {
                resultMovie.value = ApiResponse.success(jsonHelper.loadMovies())
                EspressoIdlingResource.decrement()
            },
            SERVICE_LATENCY_IN_MILLIS
        )
        return resultMovie
    }

    fun getAllTVShows(): LiveData<ApiResponse<List<MovieResponse>>> {
        EspressoIdlingResource.increment()
        val resultTVShows = MutableLiveData<ApiResponse<List<MovieResponse>>>()
        handler.postDelayed(
            {
                resultTVShows.value = ApiResponse.success(jsonHelper.loadTvShows())
                EspressoIdlingResource.decrement()
            },

            SERVICE_LATENCY_IN_MILLIS
        )
        return resultTVShows
    }

    fun getMovieById(mId: String): LiveData<ApiResponse<MovieResponse>> {
        EspressoIdlingResource.increment()
        val resultMovie = MutableLiveData<ApiResponse<MovieResponse>>()
        handler.postDelayed(
            {
                resultMovie.value = ApiResponse.success(jsonHelper.loadMoviesById(mId))
                EspressoIdlingResource.decrement()
            },
            SERVICE_LATENCY_IN_MILLIS

        )
        return resultMovie
    }

    fun getTVShowsById(mId: String): LiveData<ApiResponse<MovieResponse>> {
        EspressoIdlingResource.increment()
        val resultTVShows = MutableLiveData<ApiResponse<MovieResponse>>()
        handler.postDelayed(
            {
                resultTVShows.value = ApiResponse.success(jsonHelper.loadTvShowsById(mId))
                EspressoIdlingResource.decrement()
            },
            SERVICE_LATENCY_IN_MILLIS

        )
        return resultTVShows
    }
}