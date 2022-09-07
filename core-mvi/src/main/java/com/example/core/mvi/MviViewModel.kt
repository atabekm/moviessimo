package com.example.core.mvi

import androidx.lifecycle.ViewModel
import com.example.core.utils.dispatcher.DispatcherProvider
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container

abstract class MviViewModel<A : MviAction, S : MviState, E : MviEffect>(
    defaultState: S,
    dispatcherProvider: DispatcherProvider
) : ContainerHost<S, E>, ViewModel() {

    override val container = container<S, E>(defaultState, settings = Container.Settings(intentDispatcher = dispatcherProvider.io))

    abstract fun dispatch(action: A)
}
