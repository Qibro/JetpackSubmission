package com.mibrahim.movies.ui.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.mibrahim.movies.data.MovieRepository
import com.mibrahim.movies.data.source.local.entity.MovieEntity
import com.mibrahim.movies.utils.Constants.MOVIE_TYPE
import com.mibrahim.movies.utils.Constants.TV_SHOW_TYPE
import com.mibrahim.movies.utils.DataDummy
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner
import com.mibrahim.movies.vo.Resource

@RunWith(MockitoJUnitRunner::class)
class DetailViewModelTest {
    private lateinit var viewModel: DetailViewModel
    private var dummyMovie = Resource.success(DataDummy.generateDummyMovies()[0])
    private var dummyShows = Resource.success(DataDummy.generateDummyTVShows()[0])

    private var movieId = dummyMovie.data?.id.toString()
    private var tvShowsId = dummyShows.data?.id.toString()

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var movieRepository: MovieRepository

    @Mock
    private lateinit var observer: Observer<Resource<MovieEntity>>

    @Before
    fun setUp() {
        viewModel = DetailViewModel(movieRepository)
    }


    @Test
    fun getMovie() {
        viewModel.setSelectedMovie(movieId)
        viewModel.setType(MOVIE_TYPE)
        val movie = MutableLiveData<Resource<MovieEntity>>()
        movie.value = dummyMovie

        `when`(movieRepository.getMovie(movieId)).thenReturn(movie)

        viewModel.entity.observeForever(observer)
        verify(observer).onChanged(dummyMovie)
    }

    @Test
    fun getShow() {
        viewModel.setSelectedMovie(tvShowsId)
        viewModel.setType(TV_SHOW_TYPE)
        val tvshows = MutableLiveData<Resource<MovieEntity>>()
        tvshows.value = dummyShows

        `when`(movieRepository.getTVShow(tvShowsId)).thenReturn(tvshows)

        viewModel.entity.observeForever(observer)
        verify(observer).onChanged(dummyShows)
    }
}