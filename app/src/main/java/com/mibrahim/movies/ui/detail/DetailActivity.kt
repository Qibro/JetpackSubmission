package com.mibrahim.movies.ui.detail

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.mibrahim.movies.R
import com.mibrahim.movies.data.source.local.entity.MovieEntity
import com.mibrahim.movies.utils.Constants
import com.mibrahim.movies.viewmodel.ViewModelFactory
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.content_detail.*
import kotlinx.android.synthetic.main.content_detail.progress_bar
import java.util.*


class DetailActivity : AppCompatActivity() {

    private var menu: Menu? = null
    private lateinit var viewModel: DetailViewModel
    private lateinit var entity: MovieEntity

    companion object {
        const val EXTRA_ID = "extra_id"
        const val EXTRA_TYPE = "extra_type"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        setSupportActionBar(toolbar)

        val factory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, factory)[DetailViewModel::class.java]

        val extras = intent.extras
        if (extras != null) {
            val id = extras.getString(EXTRA_ID)
            val type = extras.getInt(EXTRA_TYPE)
            if (id != null) {
                viewModel.setSelectedMovie(id)
                viewModel.setType(type)
                viewModel.entity.observe(this, Observer {
                    if (it.data != null) {
                        entity = it.data
                        populateView(entity)
                    }
                })
            }
        }

        progress_bar.visibility = View.VISIBLE

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        this.menu = menu as Menu
        viewModel.entity.observe(this, Observer {
            if (it.data != null) {
                if (it.data.status) {
                    menu.get(0).icon =
                        ContextCompat.getDrawable(this, R.drawable.ic_baseline_favorite)
                } else {
                    menu.get(0).icon =
                        ContextCompat.getDrawable(this, R.drawable.ic_baseline_favorite_border)
                }
            }

        })
        return super.onCreateOptionsMenu(menu)
    }

    private fun setFavorite(entity: MovieEntity) {
        if (entity.status) {
            viewModel.removeFavorite()
        } else {
            viewModel.setFavorite()
        }
    }

    private fun populateView(movieEntity: MovieEntity) {
        progress_bar.visibility = View.GONE
        tv_originalTitle.text = movieEntity.title
        tv_release.text = movieEntity.releaseDate
        tv_language.text = movieEntity.originalLanguage
        tv_vote.text = movieEntity.voteAverage
        tv_desc.text = movieEntity.description
        tv_language.text = movieEntity.originalLanguage.capitalize(Locale.getDefault())
        Glide.with(this)
            .load(Constants.BASE_IMAGE_URL + movieEntity.poster)
            .apply(
                RequestOptions.placeholderOf(R.drawable.ic_loading)
                    .error(R.drawable.ic_error)
            )
            .into(img_detail)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.favorite -> {
                setFavorite(entity)
            }
        }
        return super.onOptionsItemSelected(item)
    }

}