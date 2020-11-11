package com.mibrahim.movies.ui.data.source

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.mibrahim.movies.data.source.local.LocalDataSource
import com.mibrahim.movies.data.source.local.entity.MovieEntity
import com.mibrahim.movies.data.source.remote.RemoteDataSource
import com.mibrahim.movies.data.source.remote.response.MovieResponse
import com.mibrahim.movies.ui.utils.FakeDataDummy
import com.mibrahim.movies.ui.utils.LiveDataTestUtil
import com.mibrahim.movies.ui.utils.PagedListUtil
import com.mibrahim.movies.utils.AppExecutors
import com.mibrahim.movies.utils.DataDummy
import com.mibrahim.movies.vo.Resource
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.*

class MovieRepositoryTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val remote = mock(RemoteDataSource::class.java)
    private val local = mock(LocalDataSource::class.java)
    private val appExecutors = mock(AppExecutors::class.java)

    private val movieResponses: List<MovieResponse> = FakeDataDummy.generateDummyMovies()
    private val movieId: String = movieResponses[0].id
    private val tvShowResponses: List<MovieResponse> = FakeDataDummy.generateDummyTVShows()
    private val tvShowId: String = tvShowResponses[0].id

    private val movieRepository = FakeMovieRepository(remote, local, appExecutors)

    @Test
    fun getAllMovies() {
        val datasourceFactory = mock(DataSource.Factory::class.java) as DataSource.Factory<Int,MovieEntity>
        `when`(local.getAllMovies()).thenReturn(datasourceFactory)
        movieRepository.getAllMovies()

        val movieEntities = Resource.success(PagedListUtil.mockPagedList(DataDummy.generateDummyMovies()))
        verify(local).getAllMovies()
        assertNotNull(movieEntities.data)
        assertEquals(movieResponses.size.toLong(), movieEntities.data?.size?.toLong())
    }



    @Test
    fun getMovie() {
        val dummyEntity = MutableLiveData<MovieEntity>()
        dummyEntity.value = DataDummy.generateDummyMovies().get(0)

        `when`(local.getMovieById(movieId)).thenReturn(dummyEntity)
        val result: Resource<MovieEntity> = LiveDataTestUtil.getValue(movieRepository.getMovie(movieId))
        verify(local).getMovieById(movieId)

        val response = movieResponses[0]
        val resultData: MovieEntity = result.data as MovieEntity

        Assert.assertNotNull(result.data)
        assertEquals(response.id, resultData.id)
        assertEquals(response.title, resultData.title)
        assertEquals(response.description, resultData.description)
        assertEquals(response.voteAverage, resultData.voteAverage)
        assertEquals(response.releaseDate, resultData.releaseDate)
        assertEquals(response.releaseDate, resultData.releaseDate)
        assertEquals(response.poster, resultData.poster)
    }

    @Test
    fun getAllTVShows() {
        val datasourceFactory = mock(DataSource.Factory::class.java) as DataSource.Factory<Int,MovieEntity>
        `when`(local.getAllTVShows()).thenReturn(datasourceFactory)
        movieRepository.getAllTVShows()

        val movieEntities = Resource.success(PagedListUtil.mockPagedList(DataDummy.generateDummyTVShows()))
        verify(local).getAllTVShows()
        assertNotNull(movieEntities.data)
        assertEquals(movieResponses.size.toLong(), movieEntities.data?.size?.toLong())
    }

    @Test
    fun getTVShow() {
        val dummyEntity = MutableLiveData<MovieEntity>()
        dummyEntity.value = DataDummy.generateDummyTVShows().get(0)

        `when`(local.getTVShowById(tvShowId)).thenReturn(dummyEntity)
        val result: Resource<MovieEntity> = LiveDataTestUtil.getValue(movieRepository.getTVShow(tvShowId))
        verify(local).getTVShowById(tvShowId)

        val response = tvShowResponses[0]
        val resultData: MovieEntity = result.data as MovieEntity

        Assert.assertNotNull(result.data)
        assertEquals(response.id, resultData.id)
        assertEquals(response.title, resultData.title)
        assertEquals(response.description, resultData.description)
        assertEquals(response.voteAverage, resultData.voteAverage)
        assertEquals(response.releaseDate, resultData.releaseDate)
        assertEquals(response.releaseDate, resultData.releaseDate)
        assertEquals(response.poster, resultData.poster)
    }

    @Test
    fun getFavoriteMovies(){
        val datasourceFactory = mock(DataSource.Factory::class.java) as DataSource.Factory<Int,MovieEntity>
        `when`(local.getFavoriteMovies()).thenReturn(datasourceFactory)
        movieRepository.getFavoriteMovies()

        val movieEntities = Resource.success(PagedListUtil.mockPagedList(DataDummy.generateDummyMovies()))
        verify(local).getFavoriteMovies()
        assertNotNull(movieEntities.data)
        assertEquals(movieResponses.size.toLong(), movieEntities.data?.size?.toLong())
    }

    @Test
    fun getFavoriteTVShows(){
        val datasourceFactory = mock(DataSource.Factory::class.java) as DataSource.Factory<Int,MovieEntity>
        `when`(local.getFavoriteTVShows()).thenReturn(datasourceFactory)
        movieRepository.getFavoriteTVShows()

        val tvShowsEntity = Resource.success(PagedListUtil.mockPagedList(DataDummy.generateDummyTVShows()))
        verify(local).getFavoriteTVShows()
        assertNotNull(tvShowsEntity.data)
        assertEquals(tvShowResponses.size.toLong(), tvShowsEntity.data?.size?.toLong())
    }
}