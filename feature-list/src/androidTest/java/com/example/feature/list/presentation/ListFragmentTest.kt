package com.example.feature.list.presentation

import android.os.Bundle
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.ext.junit.runners.AndroidJUnit4
import coil.Coil
import com.agoda.kakao.screen.Screen.Companion.idle
import com.agoda.kakao.screen.Screen.Companion.onScreen
import com.example.core.network.model.NetworkResponse
import com.example.core.utils.TestImageLoader
import com.example.feature.list.R
import com.example.feature.list.domain.DiscoverMoviesUseCase
import com.example.feature.list.domain.model.Movie
import com.example.feature.list.navigation.MovieListNavigation
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
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
    private val useCase = mockk<DiscoverMoviesUseCase>()
    private val movieList = (1..30).map { id ->
        Movie(id, "posterImage$id")
    }.toList()

    @Before
    fun setUp() {
        startKoin {
            modules(
                module {
                    factory { movieListNavigation }
                    factory { useCase }
                    viewModel { ListViewModel(get(), Dispatchers.Main) }
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
        coEvery { useCase.invoke() } returns NetworkResponse(true, movieList, "")

        launchFragmentInContainer<ListFragment>(Bundle(), R.style.Theme_AppCompat_Light_NoActionBar)

        onScreen<ListScreen> {
            recycler {
                isVisible()
                hasSize(movieList.size)

                firstChild<ListScreen.MovieItem> {
                    isDisplayed()
                    image {
                        isVisible()
                        hasTag(movieList.first().posterImage)
                    }
                }

                scrollToEnd()

                lastChild<ListScreen.MovieItem> {
                    isDisplayed()
                    image {
                        isVisible()
                        hasTag(movieList.last().posterImage)
                    }
                }
            }
        }
    }

    @Test
    fun verifyMovieListIsEmpty_givenNoData_thenRetry() {
        coEvery { useCase.invoke() } returns NetworkResponse(false, listOf(), "")

        launchFragmentInContainer<ListFragment>(Bundle(), R.style.Theme_AppCompat_Light_NoActionBar)

        // verify that we have no data in recycler as useCase returns unsuccessful NetworkResponse
        onScreen<ListScreen> {
            recycler {
                hasSize(0)
            }
        }

        // next call to useCase should return successful NetworkResponse
        coEvery { useCase.invoke() } returns NetworkResponse(true, movieList, "")

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
                hasSize(movieList.size)
            }
        }
    }
}
