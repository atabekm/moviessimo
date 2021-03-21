package com.example.feature.details.presentation

import android.os.Bundle
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.ext.junit.runners.AndroidJUnit4
import coil.Coil
import com.agoda.kakao.screen.Screen.Companion.idle
import com.agoda.kakao.screen.Screen.Companion.onScreen
import com.example.core.utils.TestImageLoader
import com.example.feature.details.R
import com.example.feature.details.domain.GetMovieByIdUseCase
import com.example.feature.details.domain.model.TestData.movieData
import com.example.feature.details.domain.model.TestData.movieDomain
import com.example.feature.details.domain.repository.MovieDetailRepository
import com.example.feature.details.kakao.hasAlpha
import com.example.feature.details.navigation.MovieDetailsNavigation
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
import retrofit2.Response

@RunWith(AndroidJUnit4::class)
class DetailsFragmentTest : KoinTest {
    private val movieDetailsNavigation = mockk<MovieDetailsNavigation>()
    private val repository = mockk<MovieDetailRepository>()
    private val movieId = 1234

    @Before
    fun setUp() {
        startKoin {
            modules(
                module {
                    factory { movieDetailsNavigation }
                    factory { repository }
                    factory { GetMovieByIdUseCase(get()) }
                    viewModel { DetailsViewModel(get(), Dispatchers.Main) }
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
    fun verifyMovieDetailsIsPopulated_givenValidData() {
        coEvery { repository.getMovieDetails(movieId) } returns Response.success(movieData)

        launchFragmentInContainer<DetailsFragment>(Bundle().apply {
            putInt("movie_id", movieId)
        }, R.style.Theme_AppCompat_Light_NoActionBar)

        onScreen<DetailsScreen> {
            backdrop {
                isVisible()
            }
            toolbar {
                isVisible()
            }
            genres {
                isVisible()
                hasText(movieDomain.genres)
            }
            rating {
                isVisible()
                hasRating(movieDomain.rating)
            }
            duration {
                isVisible()
                hasText(movieDomain.duration)
            }
            overview {
                isVisible()
                hasText(movieDomain.overview)
            }
            director {
                isVisible()
                hasText(movieDomain.director)
            }
            screenplay {
                isVisible()
                hasText(movieDomain.screenplay)
            }
            casting {
                isVisible()
                hasText(movieDomain.cast)
            }
            poster {
                isVisible()
                hasAlpha(1f)
                idle(1000)
            }

            // Scroll to top, so that poster becomes "invisible"
            appBar {
                collapse()
                idle(1000)
            }
            poster {
                isVisible()
                hasAlpha(0f)
            }

            // Scroll to bottom, and poster is visible again
            appBar {
                expand()
                idle(1000)
            }
            poster {
                isVisible()
                hasAlpha(1f)
            }
        }
    }
}
