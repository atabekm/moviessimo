package com.example.feature.details.domain.converter

import com.example.feature.details.domain.model.TestData.cast1
import com.example.feature.details.domain.model.TestData.cast2
import com.example.feature.details.domain.model.TestData.crew1
import com.example.feature.details.domain.model.TestData.crew2
import com.example.feature.details.domain.model.TestData.movieData
import org.junit.Assert.assertEquals
import org.junit.Test
import kotlin.math.roundToInt

class MovieConverterTest {

    @Test
    fun `verify movie conversion with valid input`() {
        val result = movieData.toDomain()
        assertEquals(movieData.id, result.id)
        assertEquals(movieData.title, result.title)
        assertEquals(movieData.overview, result.overview)
        assertEquals("Crime, Thriller, Drama", result.genres)
        assertEquals("${movieData.runtime} minutes", result.duration)
        assertEquals(crew1.name, result.director)
        assertEquals(crew2.name, result.screenplay)
        assertEquals("${cast1.name}, ${cast2.name}", result.cast)
        assertEquals(
            "https://image.tmdb.org/t/p/w500${movieData.backdropPath}",
            result.backdropImage
        )
        assertEquals("https://image.tmdb.org/t/p/w500${movieData.posterPath}", result.posterImage)
        assertEquals(movieData.releaseDate, result.releaseDate)
        assertEquals(movieData.voteAverage?.div(2)?.roundToInt()?.toFloat(), result.rating)
    }

    @Test
    fun `verify movie conversion with invalid input`() {
        val result = movieData.copy(
            id = null,
            title = null,
            overview = null,
            genres = null,
            runtime = null,
            credits = null,
            backdropPath = null,
            posterPath = null,
            releaseDate = null,
            voteAverage = null
        ).toDomain()
        assertEquals(0, result.id)
        assertEquals("", result.title)
        assertEquals("", result.overview)
        assertEquals("", result.genres)
        assertEquals("", result.duration)
        assertEquals("", result.director)
        assertEquals("", result.screenplay)
        assertEquals("", result.cast)
        assertEquals("", result.backdropImage)
        assertEquals("", result.posterImage)
        assertEquals("", result.releaseDate)
        assertEquals(0f, result.rating)
    }
}
