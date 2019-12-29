package com.example.feature.list.domain

import com.example.feature.list.data.MovieListRepository
import com.example.feature.list.data.model.DiscoverMovie
import com.example.feature.list.data.model.Movie
import io.mockk.every
import io.mockk.mockk
import io.reactivex.Observable
import org.junit.Test

class DiscoverMoviesUseCaseTest {
    private val repositoryMock = mockk<MovieListRepository>()
    private val movieData = Movie(1, "title", posterPath = "/path")
    private val movieDomain =
        com.example.feature.list.domain.model.Movie(1, "https://image.tmdb.org/t/p/w185/path")
    private val discoverMovie = DiscoverMovie(1, listOf(movieData), 0, 0)
    private val error: Throwable = Throwable("some error")
    private val useCase = DiscoverMoviesUseCase(repositoryMock)

    @Test
    fun `verify success case for DiscoverMoviesUseCase`() {
        // given
        every { repositoryMock.getDiscoverMovies() } returns Observable.just(discoverMovie)

        // when
        val result = useCase.invoke().test()

        // then
        result.assertNoErrors()
        result.assertValue(listOf(movieDomain))
    }

    @Test
    fun `verify error case for DiscoverMoviesUseCase`() {
        // given
        every { repositoryMock.getDiscoverMovies() } returns Observable.error(error)

        // when
        val result = useCase.invoke().test()

        // then
        result.assertError(error)
        result.assertNoValues()
    }
}
