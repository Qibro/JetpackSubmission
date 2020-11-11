package com.mibrahim.movies.utils

import android.content.Context
import android.util.Log
import com.mibrahim.movies.data.source.remote.response.MovieResponse
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.util.*

class JsonHelper(private val context: Context) {
    private var movieResponse:MovieResponse? = null
    private var tvShowResponse:MovieResponse? = null


    private fun parsingFileToString(fileName: String): String? {
        return try {
            val `is` = context.assets.open(fileName)
            val buffer = ByteArray(`is`.available())
            `is`.read(buffer)
            `is`.close()
            String(buffer)

        } catch (ex: IOException) {
            ex.printStackTrace()
            null
        }
    }

    fun loadMovies(): List<MovieResponse> {
        val list: ArrayList<MovieResponse> = ArrayList<MovieResponse>()
        try {
            val responseObject = JSONObject(parsingFileToString("movie_response.json").toString())
            val listArray = responseObject.getJSONArray("movies")
            for (i in 0 until listArray.length()) {
                val movie = listArray.getJSONObject(i)
                val id = movie.getString("id")
                val title = movie.getString("title")
                val releaseDate = movie.getString("release_date")
                val description = movie.getString("overview")
                val voteAverage = movie.getString("vote_average")
                val originalLanguage = movie.getString("original_language")
                val poster = movie.getString("poster_path")
                val movieResponse = MovieResponse(
                    id,
                    title,
                    description,
                    releaseDate,
                    voteAverage,
                    originalLanguage,
                    poster,
                    Constants.MOVIE_TYPE,
                )
                list.add(movieResponse)
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return list
    }

    fun loadTvShows(): List<MovieResponse> {
        val list: ArrayList<MovieResponse> = ArrayList<MovieResponse>()
        try {
            val responseObject = JSONObject(parsingFileToString("tvshow_response.json").toString())
            val listArray = responseObject.getJSONArray("tvshows")
            for (i in 0 until listArray.length()) {
                val tvShow = listArray.getJSONObject(i)
                val id = tvShow.getString("id")
                val title = tvShow.getString("name")
                val releaseDate = tvShow.getString("first_air_date")
                val description = tvShow.getString("overview")
                val voteAverage = tvShow.getString("vote_average")
                val originalLanguage = tvShow.getString("original_language")
                val poster = tvShow.getString("poster_path")
                val tvShowResponse = MovieResponse(
                    id,
                    title,
                    description,
                    releaseDate,
                    voteAverage,
                    originalLanguage,
                    poster,
                    Constants.TV_SHOW_TYPE
                )
                list.add(tvShowResponse)
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return list
    }

    fun loadMoviesById(mId: String): MovieResponse {
        try {
            val responseObject = JSONObject(parsingFileToString("movie_response.json").toString())
            Log.d("TAG", "loadMoviesById: $mId")
            val listArray = responseObject.getJSONArray("movies")
            for (i in 0 until listArray.length()) {
                val movie = listArray.getJSONObject(i)
                val id = movie.getString("id")
                if (id == mId) {
                    val title = movie.getString("title")
                    val releaseDate = movie.getString("release_date")
                    val description = movie.getString("overview")
                    val voteAverage = movie.getString("vote_average")
                    val originalLanguage = movie.getString("original_language")
                    val poster = movie.getString("poster_path")
                    movieResponse = MovieResponse(
                        id,
                        title,
                        description,
                        releaseDate,
                        voteAverage,
                        originalLanguage,
                        poster,
                        Constants.MOVIE_TYPE,
                    )
                    break
                }
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return movieResponse as MovieResponse
    }

    fun loadTvShowsById(mId : String): MovieResponse {
        try {
            val responseObject = JSONObject(parsingFileToString("tvshow_response.json").toString())
            val listArray = responseObject.getJSONArray("tvshows")
            for (i in 0 until listArray.length()) {
                val tvShow = listArray.getJSONObject(i)
                val id = tvShow.getString("id")
                if(id == mId) {
                    val title = tvShow.getString("name")
                    val releaseDate = tvShow.getString("first_air_date")
                    val description = tvShow.getString("overview")
                    val voteAverage = tvShow.getString("vote_average")
                    val originalLanguage = tvShow.getString("original_language")
                    val poster = tvShow.getString("poster_path")
                    tvShowResponse = MovieResponse(
                        id,
                        title,
                        description,
                        releaseDate,
                        voteAverage,
                        originalLanguage,
                        poster,
                        Constants.TV_SHOW_TYPE
                    )
                    break
                }

            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return tvShowResponse as MovieResponse
    }
}
