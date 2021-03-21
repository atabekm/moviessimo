package com.example.core.mvi

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

abstract class UdfStore<A : UdfAction, S : UdfState, E : UdfEffect> :
    CoroutineScope by CoroutineScope(Dispatchers.Main) {
    protected val stateFlow: MutableStateFlow<S?> = MutableStateFlow(null)
    protected val effectFlow = MutableSharedFlow<E?>()

    fun observeState(): StateFlow<S?> = stateFlow
    fun observeEffect(): Flow<E?> = effectFlow

    fun dispatch(action: A) {
        launch {
            handleAction(action)
        }
    }

    protected abstract suspend fun handleAction(action: A)
}
