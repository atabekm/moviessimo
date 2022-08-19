package com.example.feature.details.presentation

import com.example.core.mvi.MviViewModel
import com.example.core.utils.dispatcher.DispatcherProvider
import com.example.feature.details.domain.GetMovieByIdUseCase
import java.io.IOException

internal class DetailsViewModel(
    private val useCase: GetMovieByIdUseCase,
    dispatcherProvider: DispatcherProvider
) : MviViewModel<DetailsAction, DetailsState, DetailsEffect>(dispatcherProvider) {

    override suspend fun reduce(action: DetailsAction): DetailsState {
        val oldState = stateFlow.value ?: DetailsState()
        return when (action) {
            is DetailsAction.OpenMovieDetailsAction -> {
                try {
                    val result = useCase(action.movieId)
                    when (result.isSuccess) {
                        true -> oldState.copy(isLoading = false, movie = result.data, error = "")
                        false -> oldState.copy(
                            isLoading = false,
                            error = "Failed to get movie details: ${result.error}"
                        )
                    }
                } catch (e: IOException) {
                    oldState.copy(isLoading = false, error = "Failed to get movie details: ${e.localizedMessage}")
                }
            }
        }
    }
}
