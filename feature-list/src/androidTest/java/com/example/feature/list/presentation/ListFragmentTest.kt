package com.example.feature.list.presentation

import android.os.Bundle
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.ext.junit.runners.AndroidJUnit4
import coil.Coil
import com.agoda.kakao.screen.Screen.Companion.idle
import com.agoda.kakao.screen.Screen.Companion.onScreen
import com.example.core.utils.dispatcher.TestDispatcherProvider
import com.example.core.utils.image.TestImageLoader
import com.example.feature.list.R
import com.example.feature.list.data.model.DiscoverMovie
import com.example.feature.list.data.model.Movie
import com.example.feature.list.domain.DiscoverMoviesUseCase
import com.example.feature.list.domain.converter.toDomain
import com.example.feature.list.domain.repository.MovieListRepository
import com.example.feature.list.navigation.MovieListNavigation
import io.mockk.coEvery
import io.mockk.mockk
import okhttp3.internal.http.RealResponseBody
import okio.Buffer
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import retrofit2.Response

@RunWith(AndroidJUnit4::class)
class ListFragmentTest : KoinTest {
    private val movieListNavigation = mockk<MovieListNavigation>()
    private val repository = mockk<MovieListRepository>()
    private val movieListData = (1..30).map { id ->
        Movie(id, "posterImage$id")
    }.toList()
    private val movieListDomain = movieListData.map { it.toDomain() }
    private val discoverMovie = DiscoverMovie(
        1,
        movieListData,
        1,
        1
    )

    @Before
    fun setUp() {
        startKoin {
            modules(
                module {
                    factory { movieListNavigation }
                    factory { DiscoverMoviesUseCase(repository) }
                    viewModel { ListViewModel(get(), TestDispatcherProvider()) }
                }
            )
        }

        Coil.setImageLoader(TestImageLoader())
    }

    @After
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun verifyMovieListIsPopulated_givenValidData() {
        coEvery { repository.getDiscoverMovies() } returns Response.success(discoverMovie)

        launchFragmentInContainer<ListFragment>(Bundle(), R.style.Theme_AppCompat_Light_NoActionBar)

        onScreen<ListScreen> {
            recycler {
                isVisible()
                hasSize(movieListDomain.size)

                firstChild<ListScreen.MovieItem> {
                    isDisplayed()
                    image {
                        isVisible()
                        hasTag(movieListDomain.first().posterImageUrl)
                    }
                }

                scrollToEnd()

                lastChild<ListScreen.MovieItem> {
                    isDisplayed()
                    image {
                        isVisible()
                        hasTag(movieListDomain.last().posterImageUrl)
                    }
                }
            }
        }
    }

    @Test
    fun verifyMovieListIsEmpty_givenNoData_thenRetry() {
        coEvery { repository.getDiscoverMovies() } returns Response.error(400, RealResponseBody("type", 0, Buffer()))

        launchFragmentInContainer<ListFragment>(Bundle(), R.style.Theme_AppCompat_Light_NoActionBar)

        // verify that we have no data in recycler as useCase returns unsuccessful NetworkResponse
        onScreen<ListScreen> {
            recycler {
                hasSize(0)
                idle(500)
            }
        }

        // next call to useCase should return successful NetworkResponse
        coEvery { repository.getDiscoverMovies() } returns Response.success(discoverMovie)

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
