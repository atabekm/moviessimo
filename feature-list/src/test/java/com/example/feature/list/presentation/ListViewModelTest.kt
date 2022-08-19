package com.example.feature.list.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.core.network.model.NetworkResponse
import com.example.core.utils.dispatcher.TestDispatcherProvider
import com.example.feature.list.domain.DiscoverMoviesUseCase
import com.example.feature.list.domain.model.Movie
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import java.io.IOException

@OptIn(ExperimentalCoroutinesApi::class)
class ListViewModelTest {
    private val useCaseMock = mockk<DiscoverMoviesUseCase>()
    private val viewModel = ListViewModel(useCaseMock, TestDispatcherProvider())
    private val movieDomain = Movie(1, "https://image.tmdb.org/t/p/w185/path")
    private val errorMessage = "error message"

    @get:Rule
    var rule = InstantTaskExecutorRule()

    @Test
    fun `verify ListViewModel's requestMovies success scenario`() = runTest {
        // given
        coEvery { useCaseMock.invoke() } returns NetworkResponse(true, listOf(movieDomain), "")

        // when
        viewModel.dispatch(ListAction.LoadMoviesAction)

        // then
        val result = viewModel.observeState().value!!
        assertEquals(listOf(movieDomain), result.movies)
        assertEquals("", result.error)
        assertEquals(false, result.isLoading)
    }

    @Test
    fun `verify ListViewModel's requestMovies failure scenario`() = runTest {
        // given
        coEvery { useCaseMock.invoke() } returns NetworkResponse(false, listOf(), errorMessage)

        // when
        viewModel.dispatch(ListAction.LoadMoviesAction)

        // then
        val result = viewModel.observeState().value!!
        assertEquals(listOf<Movie>(), result.movies)
        assertEquals("Failed to load movies: $errorMessage", result.error)
        assertEquals(false, result.isLoading)
    }

    @Test
    fun `verify ListViewModel's requestMovies error scenario`() = runTest {
        // given
        coEvery { useCaseMock.invoke() } throws IOException()

        // when
        viewModel.dispatch(ListAction.LoadMoviesAction)

        // then
        val result = viewModel.observeState().value!!
        assertEquals(listOf<Movie>(), result.movies)
        assertEquals("Failed to load movies: null", result.error)
        assertEquals(false, result.isLoading)
    }
}
