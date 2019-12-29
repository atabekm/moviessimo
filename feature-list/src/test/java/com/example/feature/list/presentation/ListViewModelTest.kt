package com.example.feature.list.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.core.utils.scheduler.TestSchedulers
import com.example.feature.list.domain.DiscoverMoviesUseCase
import com.example.feature.list.domain.model.Movie
import io.mockk.every
import io.mockk.mockk
import io.reactivex.Observable
import org.junit.Rule
import org.junit.Test

class ListViewModelTest {
    private val useCaseMock = mockk<DiscoverMoviesUseCase>()
    private val viewModel = ListViewModel(useCaseMock, TestSchedulers())
    private val movieId = 1
    private val movieDomain = Movie(movieId, "https://image.tmdb.org/t/p/w185/path")
    private val errorMessage = "error message"

    @get:Rule
    var rule = InstantTaskExecutorRule()

    @Test
    fun `verify subscribing receives starting state`() {
        // when
        val state = viewModel.viewState.test()

        // then
        state.assertNoErrors()
        state.assertValueCount(1)
        state.assertValue(ListViewState())
    }

    @Test
    fun `verify MovieLoadEvent returns correct state given use case returns list of movies`() {
        // given
        every { useCaseMock.invoke() } returns Observable.just(listOf(movieDomain))

        // when
        val state = viewModel.viewState.test()
        val effect = viewModel.viewEffect.test()
        viewModel.processInput(ListViewEvent.MovieLoadEvent)

        // then
        state.assertNoErrors()
        state.assertValueCount(3)
        state.assertValueAt(0, ListViewState())
        state.assertValueAt(1, ListViewState(isLoading = true))
        state.assertValueAt(2, ListViewState(isLoading = false, movieList = listOf(movieDomain)))
        effect.assertNoErrors()
        effect.assertNoValues()
    }

    @Test
    fun `verify MovieLoadEvent returns correct state given use case returns error`() {
        // given
        every { useCaseMock.invoke() } returns Observable.error(Throwable(errorMessage))

        // when
        val state = viewModel.viewState.test()
        val effect = viewModel.viewEffect.test()
        viewModel.processInput(ListViewEvent.MovieLoadEvent)

        // then
        state.assertNoErrors()
        state.assertValueCount(3)
        state.assertValueAt(0, ListViewState())
        state.assertValueAt(1, ListViewState(isLoading = true))
        state.assertValueAt(2, ListViewState(isLoading = false, errorMessage = errorMessage))
        effect.assertNoErrors()
        effect.assertNoValues()
    }

    @Test
    fun `verify MovieRetryEvent returns correct state given use case returns list of movies`() {
        // given
        every { useCaseMock.invoke() } returns Observable.just(listOf(movieDomain))

        // when
        val state = viewModel.viewState.test()
        val effect = viewModel.viewEffect.test()
        viewModel.processInput(ListViewEvent.MovieRetryEvent)

        // then
        state.assertNoErrors()
        state.assertValueCount(3)
        state.assertValueAt(0, ListViewState())
        state.assertValueAt(1, ListViewState(isLoading = true))
        state.assertValueAt(2, ListViewState(isLoading = false, movieList = listOf(movieDomain)))
        effect.assertNoErrors()
        effect.assertNoValues()
    }

    @Test
    fun `verify MovieRetryEvent returns correct state given use case returns error`() {
        // given
        every { useCaseMock.invoke() } returns Observable.error(Throwable(errorMessage))

        // when
        val state = viewModel.viewState.test()
        val effect = viewModel.viewEffect.test()
        viewModel.processInput(ListViewEvent.MovieRetryEvent)

        // then
        state.assertNoErrors()
        state.assertValueCount(3)
        state.assertValueAt(0, ListViewState())
        state.assertValueAt(1, ListViewState(isLoading = true))
        state.assertValueAt(2, ListViewState(isLoading = false, errorMessage = errorMessage))
        effect.assertNoErrors()
        effect.assertNoValues()
    }

    @Test
    fun `verify MovieClickEvent returns correct effect`() {
        // when
        val state = viewModel.viewState.test()
        val effect = viewModel.viewEffect.test()
        viewModel.processInput(ListViewEvent.MovieClickEvent(movieId))

        // then
        state.assertNoErrors()
        state.assertValueCount(1)
        effect.assertNoErrors()
        effect.assertValueCount(1)
        effect.assertValue(ListViewEffect.MovieClickEffect(movieId))
    }
}
