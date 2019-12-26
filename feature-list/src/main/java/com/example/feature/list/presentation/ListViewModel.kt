package com.example.feature.list.presentation

import androidx.lifecycle.MutableLiveData
import com.example.core.network.model.Resource
import com.example.core.network.model.Status
import com.example.core.usf.UsfViewModel
import com.example.feature.list.domain.DiscoverMoviesUseCase
import com.example.feature.list.domain.model.Movie
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

class ListViewModel(
    private val useCase: DiscoverMoviesUseCase
) : UsfViewModel<ListViewEvent, ListViewResult, ListViewState, ListViewEffect>() {
    private val _movies = MutableLiveData<Resource<List<Movie>>>()

    override fun convertEventToResult(e: Observable<ListViewEvent>): Observable<Resource<ListViewResult>> {
        return Observable.merge(
            e.ofType(ListViewEvent.MovieLoadEvent::class.java).onMovieLoad(),
            e.ofType(ListViewEvent.MovieRetryEvent::class.java).onMovieRetry(),
            e.ofType(ListViewEvent.MovieClickEvent::class.java).onMovieClick()
        )
    }

    override fun convertResultToViewState(r: Observable<Resource<ListViewResult>>): Observable<ListViewState> {
        return r.scan(ListViewState()) { vs, result ->
            when (result.status) {
                Status.LOADING -> {
                    vs.copy(isLoading = true, errorMessage = "")
                }
                Status.ERROR -> {
                    vs.copy(isLoading = false, errorMessage = result.message)
                }
                Status.SUCCESS -> {
                    when (result.data) {
                        is ListViewResult.MovieLoadResult -> {
                            vs.copy(
                                isLoading = false,
                                movieList = (result.data as ListViewResult.MovieLoadResult).movies,
                                errorMessage = ""
                            )
                        }
                        is ListViewResult.MovieRetryResult -> {
                            vs.copy(
                                isLoading = false,
                                movieList = (result.data as ListViewResult.MovieRetryResult).movies,
                                errorMessage = ""
                            )
                        }
                        else -> {
                            vs.copy()
                        }
                    }
                }
            }
        }
    }

    override fun convertResultToViewEffect(r: Observable<Resource<ListViewResult>>): Observable<ListViewEffect> {
        return r.filter { it.status == Status.SUCCESS && it.data is ListViewResult.MovieClickResult }
            .map {
                ListViewEffect.MovieClickEffect((it.data as ListViewResult.MovieClickResult).movieId)
            }
    }

    private fun Observable<ListViewEvent.MovieLoadEvent>.onMovieLoad(): Observable<Resource<ListViewResult.MovieLoadResult>> {
        return switchMap {
            useCase()
                .subscribeOn(Schedulers.io())
                .map {
                    Resource.success(ListViewResult.MovieLoadResult(it))
                }
                .onErrorReturn {
                    Resource.error(it.message.toString())
                }
                .startWith(Resource.loading())
        }
    }

    private fun Observable<ListViewEvent.MovieRetryEvent>.onMovieRetry(): Observable<Resource<ListViewResult.MovieRetryResult>> {
        return switchMap {
            useCase()
                .subscribeOn(Schedulers.io())
                .map {
                    Resource.success(ListViewResult.MovieRetryResult(it))
                }
                .onErrorReturn {
                    Resource.error(it.message.toString())
                }
                .startWith(Resource.loading())
        }
    }

    private fun Observable<ListViewEvent.MovieClickEvent>.onMovieClick(): Observable<Resource<ListViewResult.MovieClickResult>> {
        return map { Resource.success(ListViewResult.MovieClickResult(it.movieId)) }
    }
}
