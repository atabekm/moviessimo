package com.example.feature.list.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.core.network.model.Resource
import com.example.core.utils.CoroutineViewModel
import com.example.feature.list.data.MovieListRepository
import com.example.feature.list.data.model.DiscoverMovie
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class ListViewModel(
    private val repository: MovieListRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : CoroutineViewModel() {

    val discoverMovieLiveData: LiveData<Resource<DiscoverMovie>> = liveData(dispatcher) {
        emit(Resource.loading())
        val result = repository.getDiscoverMovies("")
        when (result.isSuccessful) {
            true -> emit(Resource.success(result.body()))
            false -> emit(Resource.error("Couldn't load movies list"))
        }
    }
}
