package com.example.feature.details.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.core.network.model.NetworkResponse
import com.example.core.utils.dispatcher.TestDispatcherProvider
import com.example.feature.details.domain.GetMovieByIdUseCase
import com.example.feature.details.domain.model.TestData.movieDomain
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.orbitmvi.orbit.test
import java.io.IOException

@OptIn(ExperimentalCoroutinesApi::class)
class DetailsViewModelTest {
    private val useCaseMock = mockk<GetMovieByIdUseCase>()
    private val viewModel = DetailsViewModel(useCaseMock, TestDispatcherProvider())
    private val errorMessage = "error message"
    private val movieId = 123

    @get:Rule
    var rule = InstantTaskExecutorRule()

    @Test
    fun `verify DetailViewModel's getMovieDetails success scenario`() = runTest {
        // given
        coEvery { useCaseMock.invoke(movieId) } returns NetworkResponse(true, movieDomain, "")

        val testSubject = viewModel.test()

        // when
        testSubject.testIntent {
            dispatch(DetailsAction.OpenMovieDetailsAction(movieId))
        }

        // then
        testSubject.assert(DetailsState()) {
            states(
                { copy(isLoading = true) },
                { copy(movie = movieDomain, isLoading = false) }
            )
        }
    }

    @Test
    fun `verify DetailViewModel's getMovieDetails failure scenario`() = runTest {
        // given
        coEvery { useCaseMock.invoke(movieId) } returns NetworkResponse(
            false,
            movieDomain,
            errorMessage
        )

        val testSubject = viewModel.test()

        // when
        testSubject.testIntent {
            dispatch(DetailsAction.OpenMovieDetailsAction(movieId))
        }

        // then
        testSubject.assert(DetailsState()) {
            states(
                { copy(isLoading = true) },
                { copy(movie = null, isLoading = false) }
            )

            postedSideEffects(
                DetailsEffect.DetailsErrorEffect("Failed to get movie details: $errorMessage")
            )
        }
    }

    @Test
    fun `verify DetailViewModel's getMovieDetails error scenario`() = runTest {
        // given
        coEvery { useCaseMock.invoke(movieId) } throws IOException()

        val testSubject = viewModel.test()

        // when
        testSubject.testIntent {
            dispatch(DetailsAction.OpenMovieDetailsAction(movieId))
        }

        // then
        testSubject.assert(DetailsState()) {
            states(
                { copy(isLoading = true) },
                { copy(movie = null, isLoading = false) }
            )

            postedSideEffects(
                DetailsEffect.DetailsErrorEffect("Failed to get movie details: null")
            )
        }
    }
}
