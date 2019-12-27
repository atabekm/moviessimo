package com.example.feature.details.presentation

import com.example.core.network.model.Resource
import com.example.core.network.model.Status
import com.example.core.usf.UsfViewModel
import com.example.feature.details.domain.GetMovieByIdUseCase
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

class DetailsViewModel(
    private val useCase: GetMovieByIdUseCase
) : UsfViewModel<DetailViewEvent, DetailViewResult, DetailViewState, DetailViewEffect>() {

    override fun convertEventToResult(e: Observable<DetailViewEvent>): Observable<Resource<DetailViewResult>> {
        return Observable.merge(
            e.ofType(DetailViewEvent.MovieLoadEvent::class.java).onMovieLoad(),
            e.ofType(DetailViewEvent.MovieBackClickEvent::class.java).onMovieBack()
        )
    }

    override fun convertResultToViewState(r: Observable<Resource<DetailViewResult>>): Observable<DetailViewState> {
        return r.scan(DetailViewState()) { vs, result ->
            when (result.status) {
                Status.LOADING -> {
                    vs.copy(isLoading = true, errorMessage = "")
                }
                Status.ERROR -> {
                    vs.copy(isLoading = false, errorMessage = result.message)
                }
                Status.SUCCESS -> {
                    when (result.data) {
                        is DetailViewResult.MovieLoadResult -> {
                            vs.copy(
                                isLoading = false,
                                movie = (result.data as DetailViewResult.MovieLoadResult).movie,
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

    override fun convertResultToViewEffect(r: Observable<Resource<DetailViewResult>>): Observable<DetailViewEffect> {
        return r.filter { it.status == Status.SUCCESS && it.data is DetailViewResult.MovieBackClickResult }
            .map {
                DetailViewEffect.MovieBackClickEffect
            }
    }

    private fun Observable<DetailViewEvent.MovieLoadEvent>.onMovieLoad():
        Observable<Resource<DetailViewResult.MovieLoadResult>> {
        return switchMap { event ->
            useCase(event.movieId)
                .subscribeOn(Schedulers.io())
                .map {
                    Resource.success(DetailViewResult.MovieLoadResult(it))
                }
                .onErrorReturn {
                    Resource.error(it.message.toString())
                }
                .startWith(Resource.loading())
        }
    }

    private fun Observable<DetailViewEvent.MovieBackClickEvent>.onMovieBack():
        Observable<Resource<DetailViewResult.MovieBackClickResult>> {
        return map {
            Resource.success(DetailViewResult.MovieBackClickResult)
        }
    }
}
