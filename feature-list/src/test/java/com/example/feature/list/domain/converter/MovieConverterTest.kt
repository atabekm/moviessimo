package com.example.feature.list.domain.converter

import com.example.feature.list.data.model.Movie
import org.junit.Assert.assertEquals
import org.junit.Test

class MovieConverterTest {
    private val movieData = Movie(1, "title", posterPath = "/path")

    @Test
    fun `verify movie conversion works`() {
        val movieDomain = MovieConverter.fromDataToDomain(movieData)
        assertEquals(movieData.id, movieDomain.id)
        assertEquals(
            "https://image.tmdb.org/t/p/w185${movieData.posterPath}",
            movieDomain.posterImage
        )
    }
}
