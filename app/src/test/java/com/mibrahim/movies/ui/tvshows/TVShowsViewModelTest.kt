package com.mibrahim.movies.ui.tvshows

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.mibrahim.movies.data.MovieRepository
import com.mibrahim.movies.data.source.local.entity.MovieEntity
import com.mibrahim.movies.vo.Resource
import org.junit.Test
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class TVShowsViewModelTest {
    private lateinit var viewModel: TVShowsViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var tvShowRepository: MovieRepository

    @Mock
    private lateinit var observer: Observer<Resource<PagedList<MovieEntity>>>

    @Mock
    private lateinit var pagedList: PagedList<MovieEntity>

    @Before
    fun setUp() {
        viewModel = TVShowsViewModel(tvShowRepository)
    }

    @Test
    fun getTVShows() {
        val dummyTVShows = Resource.success(pagedList)
        Mockito.`when`(dummyTVShows.data?.size).thenReturn(20)
        val tvshows = MutableLiveData<Resource<PagedList<MovieEntity>>>()
        tvshows.value = dummyTVShows

        Mockito.`when`(tvShowRepository.getAllTVShows()).thenReturn(tvshows)
        val movieEntities = viewModel.getTVShows().value?.data
        Mockito.verify(tvShowRepository).getAllTVShows()
        assertNotNull(movieEntities)
        assertEquals(20, movieEntities?.size)

        viewModel.getTVShows().observeForever(observer)
        Mockito.verify(observer).onChanged(dummyTVShows)
    }
}