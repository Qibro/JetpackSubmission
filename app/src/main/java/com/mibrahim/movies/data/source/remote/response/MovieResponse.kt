package com.mibrahim.movies.data.source.remote.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class MovieResponse(
    var id: String,
    val title: String,
    val description: String,
    val releaseDate: String,
    val voteAverage: String,
    val originalLanguage:String,
    val poster: String,
    val type: Int,
) : Parcelable