package com.example.feature.list.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.network.model.Resource
import com.example.core.utils.CoroutineViewModel
import com.example.feature.list.domain.DiscoverMoviesUseCase
import com.example.feature.list.domain.model.Movie
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException

class ListViewModel(
    private val useCase: DiscoverMoviesUseCase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {
    private val _movies = MutableLiveData<Resource<List<Movie>>>()

    val movies: LiveData<Resource<List<Movie>>> = _movies

    fun requestMovies() {
        _movies.postValue(Resource.loading())
        viewModelScope.launch(dispatcher) {
            try {
                val result = useCase()

                _movies.postValue(
                    when (result.isSuccess) {
                        true -> Resource.success(result.data)
                        false -> Resource.error("Failed to load movies: ${result.error}")
                    }
                )
            } catch (e: IOException) {
                _movies.postValue(Resource.error("Failed to load movies: ${e.localizedMessage}"))
            }
        }
    }
}
