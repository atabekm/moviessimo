package com.example.feature.list.presentation

import com.example.core.mvi.MviViewModel
import com.example.core.utils.dispatcher.DispatcherProvider
import com.example.feature.list.domain.DiscoverMoviesUseCase
import java.io.IOException

internal class ListViewModel(
    private val useCase: DiscoverMoviesUseCase,
    dispatcherProvider: DispatcherProvider
) : MviViewModel<ListAction, ListState, ListEffect>(dispatcherProvider) {

    override suspend fun reduce(action: ListAction): ListState {
        val oldState = stateFlow.value ?: ListState()
        return when (action) {
            is ListAction.LoadMoviesAction -> {
                try {
                    val result = useCase()
                    when (result.isSuccess) {
                        true -> oldState.copy(movies = result.data, error = "")
                        false -> oldState.copy(error = "Failed to load movies: ${result.error}")
                    }
                } catch (e: IOException) {
                    oldState.copy(error = "Failed to load movies: ${e.localizedMessage}")
                }
            }
        }
    }
}
