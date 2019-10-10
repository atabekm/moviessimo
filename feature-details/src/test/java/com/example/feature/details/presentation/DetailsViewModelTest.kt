package com.example.feature.details.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.core.network.model.NetworkResponse
import com.example.core.network.model.Status
import com.example.feature.details.domain.GetMovieByIdUseCase
import com.example.feature.details.domain.model.TestData.movieDomain
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import java.io.IOException

class DetailsViewModelTest {
    private val useCaseMock = mockk<GetMovieByIdUseCase>()
    private val viewModel = DetailsViewModel(useCaseMock, Dispatchers.Unconfined)
    private val errorMessage = "error message"
    private val movieId = 123

    @get:Rule
    var rule = InstantTaskExecutorRule()

    @Test
    fun `verify DetailViewModel's getMovieDetails success scenario`() {
        // given
        coEvery { useCaseMock.invoke(movieId) } returns NetworkResponse(true, movieDomain, "")

        // when
        runBlocking {
            viewModel.getMovieDetails(movieId)
        }

        // then
        val result = viewModel.movie.value!!
        assertEquals(Status.SUCCESS, result.status)
        assertEquals(movieDomain, result.data)
    }

    @Test
    fun `verify DetailViewModel's getMovieDetails failure scenario`() {
        // given
        coEvery { useCaseMock.invoke(movieId) } returns NetworkResponse(
            false,
            movieDomain,
            errorMessage
        )

        // when
        runBlocking {
            viewModel.getMovieDetails(movieId)
        }

        // then
        val result = viewModel.movie.value!!
        assertEquals(Status.ERROR, result.status)
        assertEquals("Failed to get movie details: $errorMessage", result.message)
    }

    @Test
    fun `verify DetailViewModel's getMovieDetails error scenario`() {
        // given
        coEvery { useCaseMock.invoke(movieId) } throws IOException()

        // when
        runBlocking {
            viewModel.getMovieDetails(movieId)
        }

        // then
        val result = viewModel.movie.value!!
        assertEquals(Status.ERROR, result.status)
        assertEquals("Failed to get movie details: null", result.message)
    }
}
