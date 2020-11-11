package com.mibrahim.movies.ui.tvshows

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.mibrahim.movies.R
import com.mibrahim.movies.viewmodel.ViewModelFactory
import com.mibrahim.movies.vo.Status
import kotlinx.android.synthetic.main.fragment_tvshows.*
import kotlinx.android.synthetic.main.fragment_tvshows.progress_bar


class TVShowsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_tvshows, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (activity != null) {
            val factory = ViewModelFactory.getInstance(requireActivity())
            val viewModel = ViewModelProvider(this, factory)[TVShowsViewModel::class.java]
            val adapter = TVShowsAdapter()
            progress_bar.visibility = View.VISIBLE
            viewModel.getTVShows().observe(viewLifecycleOwner, { tvshow ->
                if (tvshow != null){
                    when(tvshow.status){
                        Status.LOADING -> progress_bar.visibility = View.VISIBLE
                        Status.SUCCESS ->{
                            progress_bar.visibility = View.GONE
                            adapter.submitList(tvshow.data)
                            adapter.notifyDataSetChanged()
                        }
                        Status.ERROR->{
                            progress_bar.visibility = View.GONE
                            Toast.makeText(context, "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            })
            with(rv_tvshows) {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                this.adapter = adapter
            }
        }
    }
}