package com.example.feature.details.domain

import com.example.feature.details.data.model.Movie
import com.example.feature.details.domain.model.TestData.movieData
import com.example.feature.details.domain.model.TestData.movieDomain
import com.example.feature.details.domain.repository.MovieDetailRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.internal.http.RealResponseBody
import okio.Buffer
import org.junit.Assert
import org.junit.Test
import retrofit2.Response

@OptIn(ExperimentalCoroutinesApi::class)
class GetMovieByIdUseCaseTest {
    private val repositoryMock = mockk<MovieDetailRepository>()
    private val responseSuccess: Response<Movie> = Response.success(movieData)
    private val responseError: Response<Movie> =
        Response.error(400, RealResponseBody("type", 0, Buffer()))
    private val useCase = GetMovieByIdUseCase(repositoryMock)
    private val movieId = 123

    @Test
    fun `verify success case for GetMovieByIdUseCase`() = runTest {
        // given
        coEvery { repositoryMock.getMovieDetails(movieId) } returns responseSuccess

        // when
        val result = useCase.invoke(movieId)

        // then
        Assert.assertEquals(true, result.isSuccess)
        Assert.assertEquals(movieDomain, result.data)
    }

    @Test
    fun `verify error case for GetMovieByIdUseCase`() = runTest {
        // given
        coEvery { repositoryMock.getMovieDetails(movieId) } returns responseError

        // when
        val result = useCase.invoke(movieId)

        // then
        Assert.assertEquals(false, result.isSuccess)
        Assert.assertEquals("Response.error()", result.error)
    }
}
