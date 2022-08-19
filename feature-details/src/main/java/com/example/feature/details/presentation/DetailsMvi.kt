package com.example.feature.details.presentation

import com.example.core.mvi.MviAction
import com.example.core.mvi.MviEffect
import com.example.core.mvi.MviState
import com.example.feature.details.domain.model.Movie

internal sealed class DetailsAction : MviAction {
    data class OpenMovieDetailsAction(val movieId: Int) : DetailsAction()
}

internal data class DetailsState(
    val isLoading: Boolean = false,
    val movie: Movie? = null,
    val error: String = ""
) : MviState

internal sealed class DetailsEffect : MviEffect
