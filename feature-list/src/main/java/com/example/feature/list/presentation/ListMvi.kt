package com.example.feature.list.presentation

import com.example.core.mvi.MviAction
import com.example.core.mvi.MviEffect
import com.example.core.mvi.MviState
import com.example.feature.list.domain.model.MoviePoster

internal sealed class ListAction : MviAction {
    object LoadMoviesAction : ListAction()
}

internal data class ListState(
    val isLoading: Boolean = false,
    val moviePosters: List<MoviePoster> = listOf()
) : MviState

internal sealed class ListEffect : MviEffect {
    data class ListErrorEffect(val message: String) : ListEffect()
}
