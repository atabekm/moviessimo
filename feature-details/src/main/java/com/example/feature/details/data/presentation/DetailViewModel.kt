package com.example.feature.details.data.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.core.network.model.Resource
import com.example.core.utils.CoroutineViewModel
import com.example.feature.details.data.MovieDetailRepository
import com.example.feature.details.data.model.Movie
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailViewModel(
    private val repository: MovieDetailRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : CoroutineViewModel() {
    private val _movie = MutableLiveData<Resource<Movie>>()

    val movie: LiveData<Resource<Movie>> = _movie

    fun getMovieDetails(movieId: Int) {
        _movie.postValue(Resource.loading())
        launch(dispatcher) {
            try {
                val result = repository.getMovieDetails(movieId)

                _movie.postValue(
                    when (result.isSuccessful) {
                        true -> Resource.success(result.body())
                        false -> Resource.error("Failed to load movie: ${result.errorBody()?.string()}")
                    }
                )
            } catch (e: Exception) {
                _movie.postValue(Resource.error("Failed to load movie: ${e.localizedMessage}"))
            }
        }
    }
}