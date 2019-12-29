package com.example.feature.details.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.core.utils.scheduler.TestSchedulers
import com.example.feature.details.domain.GetMovieByIdUseCase
import com.example.feature.details.domain.model.TestData.movieDomain
import io.mockk.every
import io.mockk.mockk
import io.reactivex.Observable
import org.junit.Rule
import org.junit.Test

class DetailsViewModelTest {
    private val useCaseMock = mockk<GetMovieByIdUseCase>()
    private val viewModel = DetailsViewModel(useCaseMock, TestSchedulers())
    private val errorMessage = "error message"
    private val movieId = 123

    @get:Rule
    var rule = InstantTaskExecutorRule()

    @Test
    fun `verify subscribing receives starting state`() {
        // when
        val state = viewModel.viewState.test()

        // then
        state.assertNoErrors()
        state.assertValueCount(1)
        state.assertValue(DetailViewState())
    }

    @Test
    fun `verify MovieLoadEvent returns correct state given use case returns a movie`() {
        // given
        every { useCaseMock.invoke(movieId) } returns Observable.just(movieDomain)

        // when
        val state = viewModel.viewState.test()
        val effect = viewModel.viewEffect.test()
        viewModel.processInput(DetailViewEvent.MovieLoadEvent(movieId))

        // then
        state.assertNoErrors()
        state.assertValueCount(3)
        state.assertValueAt(0, DetailViewState())
        state.assertValueAt(1, DetailViewState(isLoading = true))
        state.assertValueAt(2, DetailViewState(isLoading = false, movie = movieDomain))
        effect.assertNoErrors()
        effect.assertNoValues()
    }

    @Test
    fun `verify MovieLoadEvent returns correct state given use case returns error`() {
        // given
        every { useCaseMock.invoke(movieId) } returns Observable.error(Throwable(errorMessage))

        // when
        val state = viewModel.viewState.test()
        val effect = viewModel.viewEffect.test()
        viewModel.processInput(DetailViewEvent.MovieLoadEvent(movieId))

        // then
        state.assertNoErrors()
        state.assertValueCount(3)
        state.assertValueAt(0, DetailViewState())
        state.assertValueAt(1, DetailViewState(isLoading = true))
        state.assertValueAt(2, DetailViewState(isLoading = false, errorMessage = errorMessage))
        effect.assertNoErrors()
        effect.assertNoValues()
    }

    @Test
    fun `verify MovieBackClickEvent returns correct effect`() {
        // when
        val state = viewModel.viewState.test()
        val effect = viewModel.viewEffect.test()
        viewModel.processInput(DetailViewEvent.MovieBackClickEvent)

        // then
        state.assertNoErrors()
        state.assertValueCount(1)
        effect.assertNoErrors()
        effect.assertValueCount(1)
        effect.assertValue(DetailViewEffect.MovieBackClickEffect)
    }
}
