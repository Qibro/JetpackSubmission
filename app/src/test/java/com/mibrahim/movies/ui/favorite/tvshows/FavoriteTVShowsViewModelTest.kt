package com.mibrahim.movies.ui.favorite.tvshows

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.mibrahim.movies.data.MovieRepository
import com.mibrahim.movies.data.source.local.entity.MovieEntity
import org.junit.Test
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class FavoriteTVShowsViewModelTest {

    private lateinit var viewModel: FavoriteTVShowsViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var tvShowRepository: MovieRepository

    @Mock
    private lateinit var observer: Observer<PagedList<MovieEntity>>

    @Mock
    private lateinit var pagedList: PagedList<MovieEntity>

    @Before
    fun setUp() {
        viewModel = FavoriteTVShowsViewModel(tvShowRepository)
    }

    @Test
    fun getMovies() {
        val dummyTVShows = pagedList
        val tvshows = MutableLiveData<PagedList<MovieEntity>>()
        tvshows.value = dummyTVShows

        Mockito.`when`(tvShowRepository.getFavoriteTVShows()).thenReturn(tvshows)
        val movieEntities = viewModel.getTVShows().value
        Mockito.verify(tvShowRepository).getFavoriteTVShows()
        assertNotNull(movieEntities)

        viewModel.getTVShows().observeForever(observer)
        Mockito.verify(observer).onChanged(dummyTVShows)
    }
}