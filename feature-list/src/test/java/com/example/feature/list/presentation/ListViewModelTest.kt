package com.example.feature.list.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.core.network.model.NetworkResponse
import com.example.core.utils.dispatcher.TestDispatcherProvider
import com.example.feature.list.domain.DiscoverMoviesUseCase
import com.example.feature.list.domain.model.MoviePoster
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.orbitmvi.orbit.test
import java.io.IOException

@OptIn(ExperimentalCoroutinesApi::class)
class ListViewModelTest {
    private val useCaseMock = mockk<DiscoverMoviesUseCase>()
    private val viewModel = ListViewModel(useCaseMock, TestDispatcherProvider())
    private val movieDomain = MoviePoster(1, "title", "https://image.tmdb.org/t/p/w185/path")
    private val errorMessage = "error message"

    @get:Rule
    var rule = InstantTaskExecutorRule()

    @Test
    fun `verify ListViewModel's requestMovies success scenario`() = runTest {
        // given
        coEvery { useCaseMock.invoke() } returns NetworkResponse(true, listOf(movieDomain), "")

        val testSubject = viewModel.test()

        // when
        testSubject.testIntent {
            dispatch(ListAction.LoadMoviesAction)
        }

        // then
        testSubject.assert(ListState()) {
            states(
                { copy(isLoading = true) },
                { copy(moviePosters = listOf(movieDomain), isLoading = false) }
            )
        }
    }

    @Test
    fun `verify ListViewModel's requestMovies failure scenario`() = runTest {
        // given
        coEvery { useCaseMock.invoke() } returns NetworkResponse(false, listOf(), errorMessage)

        val testSubject = viewModel.test()

        // when
        testSubject.testIntent {
            dispatch(ListAction.LoadMoviesAction)
        }

        // then
        testSubject.assert(ListState()) {
            states(
                { copy(isLoading = true) },
                { copy(moviePosters = listOf(), isLoading = false) }
            )

            postedSideEffects(
                ListEffect.ListErrorEffect("Failed to load movies: $errorMessage")
            )
        }
    }

    @Test
    fun `verify ListViewModel's requestMovies error scenario`() = runTest {
        // given
        coEvery { useCaseMock.invoke() } throws IOException()

        val testSubject = viewModel.test()

        // when
        testSubject.testIntent {
            dispatch(ListAction.LoadMoviesAction)
        }

        // then
        testSubject.assert(ListState()) {
            states(
                { copy(isLoading = true) },
                { copy(moviePosters = listOf(), isLoading = false) }
            )

            postedSideEffects(
                ListEffect.ListErrorEffect("Failed to load movies: null")
            )
        }
    }
}
