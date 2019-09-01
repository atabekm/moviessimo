package com.example.core.utils

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

open class CoroutineViewModel : ViewModel(), CoroutineScope {
    private val viewModelJob = Job()

    override fun onCleared() {
        viewModelJob.cancel()
        super.onCleared()
    }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + viewModelJob

}