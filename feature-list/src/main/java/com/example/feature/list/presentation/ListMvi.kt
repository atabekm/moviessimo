package com.example.feature.list.presentation

import com.example.core.mvi.MviAction
import com.example.core.mvi.MviEffect
import com.example.core.mvi.MviState
import com.example.feature.list.domain.model.Movie

internal sealed class ListAction : MviAction {
    object LoadMoviesAction : ListAction()
}

internal data class ListState(
    val isLoading: Boolean = false,
    val movies: List<Movie> = listOf(),
    val error: String = ""
) : MviState

internal sealed class ListEffect : MviEffect
