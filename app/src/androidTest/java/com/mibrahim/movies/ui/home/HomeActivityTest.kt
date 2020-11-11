package com.mibrahim.movies.ui.home

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import com.mibrahim.movies.R
import com.mibrahim.movies.utils.DataDummy
import com.mibrahim.movies.utils.EspressoIdlingResource
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class HomeActivityTest {

    private val dummyMovie = DataDummy.generateDummyMovies()
    private val dummyShows = DataDummy.generateDummyTVShows()

    @get:Rule
    var activityRule = ActivityTestRule(HomeActivity::class.java)

    @Before
    fun setUp() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.espressoTestIdlingResource)
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.espressoTestIdlingResource)
    }

    @Test
    fun loadMovies() {
        onView(withId(R.id.rv_movies)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_movies)).perform(
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(dummyMovie.size)
        )
    }


    @Test
    fun loadDetailMovies() {
        onView(withId(R.id.rv_movies)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                click()
            )
        )
        onView(isRoot()).perform(swipeUp())
        onView(withId(R.id.tv_originalTitle)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_originalTitle)).check(matches(withText(dummyMovie[0].title)))
        onView(withId(R.id.tv_release)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_release)).check(matches(withText(dummyMovie[0].releaseDate)))
        onView(withId(R.id.tv_vote)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_vote)).check(matches(withText(dummyMovie[0].voteAverage)))
        onView(withId(R.id.tv_desc)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_desc)).check(matches(withText(dummyMovie[0].description)))
    }

    @Test
    fun loadTVShows() {
        onView(withId(R.id.view_pager)).perform(swipeLeft())
        onView(withId(R.id.rv_tvshows)).perform(
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(dummyShows.size)
        )
    }


    @Test
    fun loadDetailTVShows() {
        onView(withId(R.id.view_pager)).perform(swipeLeft())
        Thread.sleep(1000)
        onView(withId(R.id.rv_tvshows)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0,
            click()))
        onView(isRoot()).perform(swipeUp())
        onView(withId(R.id.tv_originalTitle)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_originalTitle)).check(matches(withText(dummyShows[0].title)))
        onView(withId(R.id.tv_release)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_release)).check(matches(withText(dummyShows[0].releaseDate)))
        onView(withId(R.id.tv_vote)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_vote)).check(matches(withText(dummyShows[0].voteAverage)))
        onView(withId(R.id.tv_desc)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_desc)).check(matches(withText(dummyShows[0].description)))
    }

    @Test
    fun loadFavorite() {
        onView(withId(R.id.favorite)).perform(click())
        onView(withId(R.id.view_pager)).perform(swipeLeft())
        onView(withId(R.id.view_pager)).perform(swipeRight())
    }

    @Test
    fun loadFavoriteMovie(){
        onView(withId(R.id.rv_movies)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                click()
            )
        )
        onView(withId(R.id.favorite)).perform(click())
        onView(isRoot()).perform(pressBack())
        onView(withId(R.id.favorite)).perform(click())
        onView(withId(R.id.rv_movies)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                click()
            )
        )
        onView(withId(R.id.favorite)).perform(click())
        onView(isRoot()).perform(pressBack())
        onView(isRoot()).perform(pressBack())
    }

    @Test
    fun loadFavoriteTVShows(){
        onView(withId(R.id.view_pager)).perform(swipeLeft())
        Thread.sleep(1000)
        onView(withId(R.id.rv_tvshows)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                click()
            )
        )
        onView(withId(R.id.favorite)).perform(click())
        onView(isRoot()).perform(pressBack())
        onView(withId(R.id.favorite)).perform(click())
        onView(withId(R.id.view_pager)).perform(swipeLeft())
        Thread.sleep(1000)
        onView(withId(R.id.rv_tvshows)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                click()
            )
        )
        onView(withId(R.id.favorite)).perform(click())
        onView(isRoot()).perform(pressBack())
        onView(isRoot()).perform(pressBack())
    }
}