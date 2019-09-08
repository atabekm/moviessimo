package com.example.feature.list.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.core.network.model.Resource
import com.example.core.utils.CoroutineViewModel
import com.example.feature.list.data.model.DiscoverMovie
import com.example.feature.list.domain.DiscoverMoviesUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ListViewModel(
    private val useCase: DiscoverMoviesUseCase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : CoroutineViewModel() {
    private val _movies = MutableLiveData<Resource<DiscoverMovie>>()

    val movies: LiveData<Resource<DiscoverMovie>> = _movies

    fun requestMovies() {
        _movies.postValue(Resource.loading())
        launch(dispatcher) {
            try {
                val result = useCase()

                _movies.postValue(
                    when (result.isSuccessful) {
                        true -> Resource.success(result.body())
                        false -> Resource.error("Failed to load movies: ${result.errorBody()?.string()}")
                    }
                )
            } catch (e : Exception) {
                _movies.postValue(Resource.error("Failed to load movies: ${e.localizedMessage}"))
            }
        }
    }
}
