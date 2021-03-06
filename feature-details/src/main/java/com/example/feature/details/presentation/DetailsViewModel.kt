package com.example.feature.details.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.network.model.Resource
import com.example.feature.details.domain.GetMovieByIdUseCase
import com.example.feature.details.domain.model.Movie
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException

internal class DetailsViewModel(
    private val useCase: GetMovieByIdUseCase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {
    private val _movie = MutableLiveData<Resource<Movie>>()

    val movie: LiveData<Resource<Movie>> = _movie

    fun getMovieDetails(movieId: Int) {
        _movie.postValue(Resource.loading())
        viewModelScope.launch(dispatcher) {
            try {
                val result = useCase(movieId)

                _movie.postValue(
                    when (result.isSuccess) {
                        true -> Resource.success(result.data)
                        false -> Resource.error("Failed to get movie details: ${result.error}")
                    }
                )
            } catch (e: IOException) {
                _movie.postValue(Resource.error("Failed to get movie details: ${e.localizedMessage}"))
            }
        }
    }
}
