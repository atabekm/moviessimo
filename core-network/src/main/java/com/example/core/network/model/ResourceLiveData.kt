package com.example.core.network.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import retrofit2.Response
import kotlin.coroutines.CoroutineContext

/**
 * This is a util method to simplify network calls, which converts results to [Resource] object and
 * wraps with LiveData
 */
fun <T> resourceLiveData(
    context: CoroutineContext,
    block: suspend () -> Response<T>
): LiveData<Resource<T>> {
    return liveData(context) {
        emit(Resource.loading())
        val result = block.invoke()
        when (result.isSuccessful) {
            true -> emit(Resource.success(result.body()))
            false -> emit(Resource.error("Couldn't load the data"))
        }
    }
}