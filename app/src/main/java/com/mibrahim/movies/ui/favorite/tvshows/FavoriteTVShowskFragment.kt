package com.mibrahim.movies.ui.favorite.tvshows

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.mibrahim.movies.R
import com.mibrahim.movies.data.source.local.entity.MovieEntity
import com.mibrahim.movies.ui.tvshows.TVShowsAdapter
import com.mibrahim.movies.viewmodel.ViewModelFactory
import kotlinx.android.synthetic.main.fragment_tvshows.*
import kotlinx.android.synthetic.main.fragment_tvshows.progress_bar
import kotlinx.android.synthetic.main.fragment_tvshows.tv_empty


class FavoriteTVShowskFragment : Fragment() {
    private val adapter = TVShowsAdapter()
    private lateinit var viewModel: FavoriteTVShowsViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_tvshows, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (activity != null) {
            itemTouchHelper.attachToRecyclerView(rv_tvshows)
            val factory = ViewModelFactory.getInstance(requireActivity())
            viewModel = ViewModelProvider(this, factory)[FavoriteTVShowsViewModel::class.java]
            progress_bar.visibility = View.VISIBLE
            getFavorite()
            with(rv_tvshows) {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                this.adapter = this@FavoriteTVShowskFragment.adapter
            }
        }
    }

    override fun onResume() {
        super.onResume()
        getFavorite()
    }

    private fun getFavorite() {
        viewModel.getTVShows().observe(viewLifecycleOwner, { tvshow ->
            if (tvshow != null) {
                if (tvshow.size == 0) tv_empty.visibility = View.VISIBLE
                else tv_empty.visibility = View.GONE
                progress_bar.visibility = View.GONE
                adapter.submitList(tvshow)
                adapter.notifyDataSetChanged()

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
                val movieEntity = adapter.getSwipedData(swipedPosition)
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