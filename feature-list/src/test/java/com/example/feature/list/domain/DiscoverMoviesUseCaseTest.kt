package com.example.feature.list.domain

import com.example.feature.list.data.model.DiscoverMovie
import com.example.feature.list.data.model.Movie
import com.example.feature.list.domain.repository.MovieListRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.internal.http.RealResponseBody
import okio.Buffer
import org.junit.Assert.assertEquals
import org.junit.Test
import retrofit2.Response

@OptIn(ExperimentalCoroutinesApi::class)
class DiscoverMoviesUseCaseTest {
    private val repositoryMock = mockk<MovieListRepository>()
    private val movieData = Movie(1, "title", posterPath = "/path")
    private val movieDomain =
        com.example.feature.list.domain.model.MoviePoster(1, "title", "https://image.tmdb.org/t/p/w185/path")
    private val discoverMovie = DiscoverMovie(1, listOf(movieData), 0, 0)
    private val responseSuccess: Response<DiscoverMovie> = Response.success(discoverMovie)
    private val responseError: Response<DiscoverMovie> =
        Response.error(400, RealResponseBody("type", 0, Buffer()))
    private val useCase = DiscoverMoviesUseCase(repositoryMock)

    @Test
    fun `verify success case for DiscoverMoviesUseCase`() = runTest {
        // given
        coEvery { repositoryMock.getDiscoverMovies() } returns responseSuccess

        // when
        val result = useCase()

        // then
        assertEquals(true, result.isSuccess)
        assertEquals(listOf(movieDomain), result.data)
    }

    @Test
    fun `verify error case for DiscoverMoviesUseCase`() = runTest {
        // given
        coEvery { repositoryMock.getDiscoverMovies() } returns responseError

        // when
        val result = useCase()

        // then
        assertEquals(false, result.isSuccess)
        assertEquals("Response.error()", result.error)
    }
}
