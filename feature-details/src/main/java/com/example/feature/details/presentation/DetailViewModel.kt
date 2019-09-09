package com.example.feature.details.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.core.network.model.Resource
import com.example.core.utils.CoroutineViewModel
import com.example.feature.details.data.model.Movie
import com.example.feature.details.domain.GetMovieByIdUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException

class DetailViewModel(
    private val useCase: GetMovieByIdUseCase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : CoroutineViewModel() {
    private val _movie = MutableLiveData<Resource<Movie>>()

    val movie: LiveData<Resource<Movie>> = _movie

    fun getMovieDetails(movieId: Int) {
        _movie.postValue(Resource.loading())
        launch(dispatcher) {
            try {
                val result = useCase(movieId)

                _movie.postValue(
                    when (result.isSuccessful) {
                        true -> Resource.success(result.body())
                        false -> Resource.error("Failed to load movie: ${result.errorBody()?.string()}")
                    }
                )
            } catch (e: IOException) {
                _movie.postValue(Resource.error("Failed to load movie: ${e.localizedMessage}"))
            }
        }
    }
}
