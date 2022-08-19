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
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
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

        // when
        viewModel.dispatch(DetailsAction.OpenMovieDetailsAction(movieId))

        // then
        val result = viewModel.observeState().value!!
        assertEquals(movieDomain, result.movie)
        assertEquals("", result.error)
        assertEquals(false, result.isLoading)
    }

    @Test
    fun `verify DetailViewModel's getMovieDetails failure scenario`() = runTest {
        // given
        coEvery { useCaseMock.invoke(movieId) } returns NetworkResponse(
            false,
            movieDomain,
            errorMessage
        )

        // when
        viewModel.dispatch(DetailsAction.OpenMovieDetailsAction(movieId))

        // then
        val result = viewModel.observeState().value!!
        assertEquals(null, result.movie)
        assertEquals("Failed to get movie details: $errorMessage", result.error)
        assertEquals(false, result.isLoading)
    }

    @Test
    fun `verify DetailViewModel's getMovieDetails error scenario`() {
        // given
        coEvery { useCaseMock.invoke(movieId) } throws IOException()

        // when
        viewModel.dispatch(DetailsAction.OpenMovieDetailsAction(movieId))

        // then
        val result = viewModel.observeState().value!!
        assertEquals(null, result.movie)
        assertEquals("Failed to get movie details: null", result.error)
        assertEquals(false, result.isLoading)
    }
}
