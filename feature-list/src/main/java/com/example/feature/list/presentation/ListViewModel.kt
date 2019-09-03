package com.example.feature.list.presentation

import androidx.lifecycle.LiveData
import com.example.core.network.model.Resource
import com.example.core.network.model.resourceLiveData
import com.example.core.utils.CoroutineViewModel
import com.example.feature.list.data.MovieListRepository
import com.example.feature.list.data.model.DiscoverMovie
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class ListViewModel(
    private val repository: MovieListRepository,
    dispatcher: CoroutineDispatcher = Dispatchers.IO
) : CoroutineViewModel() {

    val discoverMovieLiveData: LiveData<Resource<DiscoverMovie>> = resourceLiveData(dispatcher) {
        repository.getDiscoverMovies()
    }
}
