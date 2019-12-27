package com.example.feature.details.presentation

import com.example.core.usf.ViewEffect
import com.example.core.usf.ViewEvent
import com.example.core.usf.ViewResult
import com.example.core.usf.ViewState
import com.example.feature.details.domain.model.Movie

sealed class DetailViewEvent : ViewEvent {
    data class MovieLoadEvent(val movieId: Int) : DetailViewEvent()
    object MovieBackClickEvent : DetailViewEvent()
}

sealed class DetailViewResult : ViewResult {
    data class MovieLoadResult(val movie: Movie) : DetailViewResult()
    object MovieBackClickResult : DetailViewResult()
}

data class DetailViewState(
    val isLoading: Boolean = false,
    val movie: Movie = Movie.EMPTY,
    val errorMessage: String = ""
) : ViewState

sealed class DetailViewEffect : ViewEffect {
    object MovieBackClickEffect : DetailViewEffect()
}
