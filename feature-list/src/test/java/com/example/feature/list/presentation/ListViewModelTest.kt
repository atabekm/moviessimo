package com.example.feature.list.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.core.network.model.NetworkResponse
import com.example.core.network.model.Status
import com.example.feature.list.domain.DiscoverMoviesUseCase
import com.example.feature.list.domain.model.Movie
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import java.io.IOException

class ListViewModelTest {
    private val useCaseMock = mockk<DiscoverMoviesUseCase>()
    private val viewModel = ListViewModel(useCaseMock, Dispatchers.Unconfined)
    private val movieDomain = Movie(1, "https://image.tmdb.org/t/p/w185/path")
    private val errorMessage = "error message"

    @get:Rule
    var rule = InstantTaskExecutorRule()

    @Test
    fun `verify ListViewModel's requestMovies success scenario`() {
        // given
        coEvery { useCaseMock.invoke() } returns NetworkResponse(true, listOf(movieDomain), "")

        // when
        runBlocking {
            viewModel.requestMovies()
        }

        // then
        val result = viewModel.movies.value!!
        assertEquals(Status.SUCCESS, result.status)
        assertEquals(listOf(movieDomain), result.data)
    }

    @Test
    fun `verify ListViewModel's requestMovies failure scenario`() {
        // given
        coEvery { useCaseMock.invoke() } returns NetworkResponse(false, listOf(), errorMessage)

        // when
        runBlocking {
            viewModel.requestMovies()
        }

        // then
        val result = viewModel.movies.value!!
        assertEquals(Status.ERROR, result.status)
        assertEquals("Failed to load movies: $errorMessage", result.message)
    }

    @Test
    fun `verify ListViewModel's requestMovies error scenario`() {
        // given
        coEvery { useCaseMock.invoke() } throws IOException()

        // when
        runBlocking {
            viewModel.requestMovies()
        }

        // then
        val result = viewModel.movies.value!!
        assertEquals(Status.ERROR, result.status)
        assertEquals("Failed to load movies: null", result.message)
    }
}
