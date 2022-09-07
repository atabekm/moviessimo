package com.example.feature.list.presentation

import com.example.core.mvi.MviViewModel
import com.example.core.utils.dispatcher.DispatcherProvider
import com.example.feature.list.domain.DiscoverMoviesUseCase
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import java.io.IOException

internal class ListViewModel(
    private val useCase: DiscoverMoviesUseCase,
    dispatcherProvider: DispatcherProvider
) : MviViewModel<ListAction, ListState, ListEffect>(ListState(), dispatcherProvider) {

    override fun dispatch(action: ListAction) = intent {
        when (action) {
            is ListAction.LoadMoviesAction -> {
                reduce {
                    state.copy(isLoading = true)
                }

                val newState: ListState = try {
                    val result = useCase()
                    when (result.isSuccess) {
                        true -> {
                            state.copy(moviePosters = result.data, isLoading = false)
                        }
                        false -> {
                            postSideEffect(ListEffect.ListErrorEffect("Failed to load movies: ${result.error}"))
                            state.copy(isLoading = false)
                        }
                    }
                } catch (e: IOException) {
                    postSideEffect(ListEffect.ListErrorEffect("Failed to load movies: ${e.localizedMessage}"))
                    state.copy(isLoading = false)
                }

                reduce {
                    state.copy(moviePosters = newState.moviePosters, isLoading = newState.isLoading)
                }
            }
        }
    }
}
