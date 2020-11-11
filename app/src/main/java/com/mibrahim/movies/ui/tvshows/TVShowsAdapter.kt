package com.mibrahim.movies.ui.tvshows

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.mibrahim.movies.R
import com.mibrahim.movies.data.source.local.entity.MovieEntity
import com.mibrahim.movies.ui.detail.DetailActivity
import com.mibrahim.movies.utils.Constants
import kotlinx.android.synthetic.main.items_movies.view.*
import java.util.*

class TVShowsAdapter :
    PagedListAdapter<MovieEntity, TVShowsAdapter.TVShowsViewHolder>(DIFF_CALLBACK) {
    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<MovieEntity>() {
            override fun areItemsTheSame(oldItem: MovieEntity, newItem: MovieEntity): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: MovieEntity, newItem: MovieEntity): Boolean {
                return oldItem == newItem
            }
        }
    }

    fun getSwipedData(swipedPosition: Int): MovieEntity? = getItem(swipedPosition)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TVShowsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.items_movies, parent, false)
        return TVShowsViewHolder(view)
    }

    override fun onBindViewHolder(holder: TVShowsViewHolder, position: Int) {
        val tvshows = getItem(position)
        if (tvshows != null) {
            holder.bind(tvshows)
        }
    }


    inner class TVShowsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(tvshows: MovieEntity) {
            with(itemView) {
                tv_titles.text = tvshows.title
                tv_release.text = tvshows.releaseDate.substring(0..3)
                tv_lang.text = tvshows.originalLanguage.capitalize(Locale.getDefault())
                tv_rating.text = tvshows.voteAverage
                tv_description.text = tvshows.description
                setOnClickListener {
                    val intent = Intent(context, DetailActivity::class.java).apply {
                        putExtra(DetailActivity.EXTRA_ID, tvshows.id)
                        putExtra(DetailActivity.EXTRA_TYPE, 2)
                    }
                    context.startActivity(intent)
                }
                Glide.with(itemView.context)
                    .load(Constants.BASE_IMAGE_URL + tvshows.poster)
                    .apply(
                        RequestOptions.placeholderOf(R.drawable.ic_loading)
                            .error(R.drawable.ic_error)
                    )
                    .into(img_movies)
            }
        }
    }
}

