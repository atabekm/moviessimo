package com.example.core.usf

import androidx.lifecycle.ViewModel
import com.example.core.network.model.Resource
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.PublishSubject

abstract class UsfViewModel<E: ViewEvent, R: ViewResult, S: ViewState, F: ViewEffect> : ViewModel() {
    private val eventEmitter: PublishSubject<E> = PublishSubject.create()

    private lateinit var disposable: Disposable

    val viewState: Observable<S>
    val viewEffect: Observable<F>

    init {
        eventEmitter
            .doOnNext { println("UdfViewModel: eventEmitter, event: $it") }
            .eventToResult()
            .doOnNext { println("UdfViewModel: eventEmitter, result: $it") }
            .share()
            .also { result ->
                viewState = result
                    .doOnNext { println("UdfViewModel: viewState, result: $it") }
                    .resultToViewState()
                    .doOnNext { println("UdfViewModel: viewState, state: $it") }
                    .replay(1)
                    .autoConnect(1) {
                        disposable = it
                    }

                viewEffect = result
                    .doOnNext { println("UdfViewModel: viewEffect, result: $it") }
                    .resultToViewEffect()
                    .doOnNext { println("UdfViewModel: viewEffect, effect: $it") }
            }
    }

    override fun onCleared() {
        disposable.dispose()
        super.onCleared()
    }

    fun processInput(event: E) {
        eventEmitter.onNext(event)
    }

    abstract fun convertEventToResult(e: Observable<E>): Observable<Resource<R>>

    abstract fun convertResultToViewState(r: Observable<Resource<R>>): Observable<S>

    abstract fun convertResultToViewEffect(r: Observable<Resource<R>>): Observable<F>

    private fun Observable<E>.eventToResult(): Observable<Resource<R>> {
        return publish { o ->
            convertEventToResult(o)
        }
    }

    private fun Observable<Resource<R>>.resultToViewState(): Observable<S> {
        return convertResultToViewState(this)
            .distinctUntilChanged()
    }

    private fun Observable<Resource<R>>.resultToViewEffect(): Observable<F> {
        return convertResultToViewEffect(this)
    }
}
