package com.example.feature.details.domain

import com.example.feature.details.data.MovieDetailRepository
import com.example.feature.details.domain.model.TestData.movieData
import com.example.feature.details.domain.model.TestData.movieDomain
import io.mockk.every
import io.mockk.mockk
import io.reactivex.Observable
import org.junit.Test

class GetMovieByIdUseCaseTest {
    private val repositoryMock = mockk<MovieDetailRepository>()
    private val useCase = GetMovieByIdUseCase(repositoryMock)
    private val movieId = 123
    private val error: Throwable = Throwable("some error")

    @Test
    fun `verify success case for GetMovieByIdUseCase`() {
        // given
        every { repositoryMock.getMovieDetails(movieId) } returns Observable.just(movieData)

        // when
        val result = useCase.invoke(movieId).test()

        // then
        result.assertNoErrors()
        result.assertValue(movieDomain)
    }

    @Test
    fun `verify error case for GetMovieByIdUseCase`() {
        // given
        every { repositoryMock.getMovieDetails(movieId) } returns Observable.error(error)

        // when
        val result = useCase.invoke(movieId).test()

        // then
        result.assertError(error)
        result.assertNoValues()
    }
}
