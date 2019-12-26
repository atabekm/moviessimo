package com.example.feature.list.presentation

import com.example.core.usf.ViewEffect
import com.example.core.usf.ViewEvent
import com.example.core.usf.ViewResult
import com.example.core.usf.ViewState
import com.example.feature.list.domain.model.Movie

sealed class ListViewEvent : ViewEvent {
    object MovieLoadEvent : ListViewEvent()
    object MovieRetryEvent : ListViewEvent()
    data class MovieClickEvent(val movieId: Int) : ListViewEvent()
}

sealed class ListViewResult : ViewResult {
    data class MovieLoadResult(val movies: List<Movie>) : ListViewResult()
    data class MovieRetryResult(val movies: List<Movie>) : ListViewResult()
    data class MovieClickResult(val movieId: Int) : ListViewResult()
}

data class ListViewState(
    val isLoading: Boolean = false,
    val movieList: List<Movie> = emptyList(),
    val errorMessage: String = ""
) : ViewState

sealed class ListViewEffect : ViewEffect {
    data class MovieClickEffect(val movieId: Int) : ListViewEffect()
}
