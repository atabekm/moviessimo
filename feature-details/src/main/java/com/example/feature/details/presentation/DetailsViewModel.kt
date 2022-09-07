package com.example.feature.details.presentation

import com.example.core.mvi.MviViewModel
import com.example.core.utils.dispatcher.DispatcherProvider
import com.example.feature.details.domain.GetMovieByIdUseCase
import kotlinx.coroutines.withContext
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import java.io.IOException

internal class DetailsViewModel(
    private val useCase: GetMovieByIdUseCase,
    private val dispatcherProvider: DispatcherProvider
) : MviViewModel<DetailsAction, DetailsState, DetailsEffect>(DetailsState(), dispatcherProvider) {

    override fun dispatch(action: DetailsAction) = intent {
        when (action) {
            is DetailsAction.OpenMovieDetailsAction -> {
                reduce {
                    state.copy(isLoading = true)
                }
                val newState: DetailsState = try {
                    val result = withContext(dispatcherProvider.io) {
                        useCase(action.movieId)
                    }
                    when (result.isSuccess) {
                        true -> {
                            state.copy(isLoading = false, movie = result.data)
                        }
                        false -> {
                            postSideEffect(DetailsEffect.DetailsErrorEffect("Failed to get movie details: ${result.error}"))
                            state.copy(isLoading = false)
                        }
                    }
                } catch (e: IOException) {
                    postSideEffect(DetailsEffect.DetailsErrorEffect("Failed to get movie details: ${e.localizedMessage}"))
                    state.copy(isLoading = false)
                }

                reduce {
                    state.copy(isLoading = newState.isLoading, movie = newState.movie)
                }
            }
        }
    }
}
