package com.example.feature.list.presentation

import android.os.Bundle
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.ext.junit.runners.AndroidJUnit4
import coil.Coil
import com.agoda.kakao.screen.Screen.Companion.idle
import com.agoda.kakao.screen.Screen.Companion.onScreen
import com.example.core.utils.TestImageLoader
import com.example.core.utils.scheduler.AppSchedulers
import com.example.core.utils.scheduler.Schedulers
import com.example.feature.list.R
import com.example.feature.list.data.MovieListRepository
import com.example.feature.list.data.model.DiscoverMovie
import com.example.feature.list.data.model.Movie
import com.example.feature.list.domain.DiscoverMoviesUseCase
import com.example.feature.list.domain.converter.MovieConverter
import com.example.feature.list.navigation.MovieListNavigation
import io.mockk.every
import io.mockk.mockk
import io.reactivex.Observable
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest

@RunWith(AndroidJUnit4::class)
class ListFragmentTest : KoinTest {
    private val movieListNavigation = mockk<MovieListNavigation>()
    private val repository = mockk<MovieListRepository>()
    private val movieListData = (1..30).map { id ->
        Movie(id, "posterImage$id")
    }.toList()
    private val movieListDomain = movieListData.map { MovieConverter.fromDataToDomain(it) }
    private val discoverMovie = DiscoverMovie(1, movieListData, 1, 1)

    @Before
    fun setUp() {
        startKoin {
            modules(
                module {
                    factory { movieListNavigation }
                    factory { DiscoverMoviesUseCase(repository) }
                    factory<Schedulers> { AppSchedulers() }
                    viewModel { ListViewModel(get(), get()) }
                }
            )
        }

        Coil.setDefaultImageLoader {
            TestImageLoader()
        }
    }

    @After
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun verifyMovieListIsPopulated_givenValidData() {
        every { repository.getDiscoverMovies() } returns Observable.just(discoverMovie)

        launchFragmentInContainer<ListFragment>(Bundle(), R.style.Theme_AppCompat_Light_NoActionBar)

        onScreen<ListScreen> {
            recycler {
                isVisible()
                hasSize(movieListDomain.size)

                firstChild<ListScreen.MovieItem> {
                    isDisplayed()
                    image {
                        isVisible()
                        hasTag(movieListDomain.first().posterImage)
                    }
                }

                scrollToEnd()

                lastChild<ListScreen.MovieItem> {
                    isDisplayed()
                    image {
                        isVisible()
                        hasTag(movieListDomain.last().posterImage)
                    }
                }
            }
        }
    }

    @Test
    fun verifyMovieListIsEmpty_givenNoData_thenRetry() {
        every { repository.getDiscoverMovies() } returns Observable.error(Throwable("some error"))

        launchFragmentInContainer<ListFragment>(Bundle(), R.style.Theme_AppCompat_Light_NoActionBar)

        // verify that we have no data in recycler as useCase returns unsuccessful NetworkResponse
        onScreen<ListScreen> {
            recycler {
                hasSize(0)
                idle(500)
            }
        }

        // next call to useCase should return successful NetworkResponse
        every { repository.getDiscoverMovies() } returns Observable.just(discoverMovie)

        onScreen<ListScreen> {
            // show snackbar when we have problems retrieving data from network
            snackBar {
                isDisplayed()

                action {
                    hasText("Retry")
                    // click retry
                    click()
                    idle(500)
                }

                doesNotExist()
            }

            // verify we got proper results
            recycler {
                hasSize(movieListDomain.size)
            }
        }
    }
}
