package com.example.feature.list.domain

import com.example.feature.list.data.MovieListRepository
import com.example.feature.list.data.model.DiscoverMovie
import com.example.feature.list.data.model.Movie
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import okhttp3.internal.http.RealResponseBody
import org.junit.Assert.assertEquals
import org.junit.Test
import retrofit2.Response

class DiscoverMoviesUseCaseTest {
    private val repositoryMock = mockk<MovieListRepository>()
    private val movieData = Movie(1, "title", posterPath = "/path")
    private val movieDomain =
        com.example.feature.list.domain.model.Movie(1, "https://image.tmdb.org/t/p/w185/path")
    private val discoverMovie = DiscoverMovie(1, listOf(movieData), 0, 0)
    private val responseSuccess: Response<DiscoverMovie> = Response.success(discoverMovie)
    private val responseError: Response<DiscoverMovie> =
        Response.error(400, RealResponseBody("type", 0, null))
    private val useCase = DiscoverMoviesUseCase(repositoryMock)

    @Test
    fun `verify success case for DiscoverMoviesUseCase`() {
        // given
        coEvery { repositoryMock.getDiscoverMovies() } returns responseSuccess

        // when
        val result = runBlocking { useCase.invoke() }

        // then
        assertEquals(true, result.isSuccess)
        assertEquals(listOf(movieDomain), result.data)
    }

    @Test
    fun `verify error case for DiscoverMoviesUseCase`() {
        // given
        coEvery { repositoryMock.getDiscoverMovies() } returns responseError

        // when
        val result = runBlocking { useCase.invoke() }

        // then
        assertEquals(false, result.isSuccess)
        assertEquals("Response.error()", result.error)
    }
}
