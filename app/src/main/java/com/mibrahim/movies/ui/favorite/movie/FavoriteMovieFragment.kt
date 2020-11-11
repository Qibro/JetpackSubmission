package com.mibrahim.movies.ui.favorite.movie

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.mibrahim.movies.R
import com.mibrahim.movies.data.source.local.entity.MovieEntity
import com.mibrahim.movies.ui.movie.MovieAdapter
import com.mibrahim.movies.viewmodel.ViewModelFactory
import kotlinx.android.synthetic.main.fragment_movies.*

class FavoriteMovieFragment : Fragment() {
    private lateinit var viewModel: FavoriteMovieViewModel
    private val movieAdapter = MovieAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(R.layout.fragment_movies, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (activity != null) {
            itemTouchHelper.attachToRecyclerView(rv_movies)
            val factory = ViewModelFactory.getInstance(requireActivity())
            viewModel = ViewModelProvider(this, factory)[FavoriteMovieViewModel::class.java]
            progress_bar.visibility = View.VISIBLE
            getMovie()
            with(rv_movies) {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = movieAdapter
            }
        }
    }

    override fun onResume() {
        super.onResume()
        getMovie()
    }

    private fun getMovie(){
        viewModel.getMovies().observe(viewLifecycleOwner, { movies ->
            if (movies != null) {
                if(movies.size == 0) tv_empty.visibility = View.VISIBLE
                else tv_empty.visibility = View.GONE
                progress_bar.visibility = View.GONE
                movieAdapter.submitList(movies)
                movieAdapter.notifyDataSetChanged()
            }
        })
    }

    private val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.Callback() {
        override fun getMovementFlags(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder
        ): Int = makeMovementFlags(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT)

        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean = true

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            if (view != null) {
                val swipedPosition = viewHolder.adapterPosition
                val movieEntity = movieAdapter.getSwipedData(swipedPosition)
                movieEntity?.let {
                    viewModel.removeFavorite(movieEntity)
                }
                val snackBar =
                    Snackbar.make(view as View, R.string.message_undo, Snackbar.LENGTH_LONG)
                snackBar.setAction(R.string.message_ok) { v ->
                    movieEntity.let { viewModel.removeFavorite(it as MovieEntity) }
                }
                snackBar.show()
            }
        }

    })
}