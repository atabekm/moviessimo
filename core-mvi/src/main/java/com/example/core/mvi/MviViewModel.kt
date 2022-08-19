package com.example.core.mvi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.utils.dispatcher.DispatcherProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

abstract class MviViewModel<A : MviAction, S : MviState, E : MviEffect>(
    private val dispatcherProvider: DispatcherProvider
) : ViewModel() {
    protected val stateFlow: MutableStateFlow<S?> = MutableStateFlow(null)
    protected val effectFlow = MutableSharedFlow<E?>()

    fun observeState(): StateFlow<S?> = stateFlow
    fun observeEffect(): Flow<E?> = effectFlow

    fun dispatch(action: A) {
        viewModelScope.launch(dispatcherProvider.main) {
            val oldState = stateFlow.value
            val newState = withContext(dispatcherProvider.io) {
                reduce(action)
            }
            if (oldState != newState) {
                stateFlow.value = newState
            }
        }
    }

    protected abstract suspend fun reduce(action: A): S
}
