package com.example.feature.details.domain.model

import com.example.feature.details.data.model.Cast
import com.example.feature.details.data.model.Credit
import com.example.feature.details.data.model.Crew
import com.example.feature.details.data.model.Genre
import com.example.feature.details.data.model.Language
import com.example.feature.details.data.model.Movie
import com.example.feature.details.domain.converter.toDomain

internal object TestData {
    private const val GENRE_CRIME_ID = 80
    private const val GENRE_THRILLER_ID = 53
    private const val GENRE_DRAMA_ID = 18

    val cast1 = Cast(
        castId = 2,
        character = "Arthur Fleck / Joker",
        creditId = "5a88f80a9251410b4d05826b",
        gender = 2,
        id = 73421,
        name = "Joaquin Phoenix",
        order = 0,
        profilePath = "/zixTWuMZ1D8EopgOhLVZ6Js2ux3.jpg"
    )
    val cast2 = Cast(
        castId = 15,
        character = "Sophie Dumond",
        creditId = "5b5122a00e0a262596006a4c",
        gender = 1,
        id = 1545693,
        name = "Zazie Beetz",
        order = 1,
        profilePath = "/sgxzT54GnvgeMnOZgpQQx9csAdd.jpg"
    )
    val crew1 = Crew(
        creditId = "5d2ddcd6caab6d31b2988b81",
        department = "Directing",
        gender = 2,
        id = 57130,
        job = "Director",
        name = "Todd Phillips",
        profilePath = "/qIoZhWDXSnMXvfuqwKZE8KAYOXu.jpg"
    )
    val crew2 = Crew(
        creditId = "5c6dd8aa0e0a262c99a1aed3",
        department = "Writing",
        gender = 2,
        id = 324,
        job = "Writer",
        name = "Scott Silver",
        profilePath = "/vRblvymfxaMd7Lsqs4ypVAfnJK.jpg"
    )

    val movieData = Movie(
        adult = false,
        id = 475557,
        budget = 55000000,
        genres = listOf(
            Genre(GENRE_CRIME_ID, "Crime"),
            Genre(GENRE_THRILLER_ID, "Thriller"),
            Genre(GENRE_DRAMA_ID, "Drama")
        ),
        homepage = "http://www.jokermovie.net",
        overview = "During the 1980s, a failed stand-up comedian is driven insane and turns to a life of crime and " +
            "chaos in Gotham City while becoming an infamous psychopathic crime figure.",
        popularity = 261.285,
        revenue = 0,
        runtime = 122,
        status = "Released",
        tagline = "Put on a happy face",
        title = "Joker",
        video = false,
        credits = Credit(cast = listOf(
            cast1,
            cast2
        ), crew = listOf(
            crew1,
            crew2
        )),
        backdropPath = "/hO7KbdvGOtDdeg0W4Y5nKEHeDDh.jpg",
        belongsToCollection = null,
        imdbId = "tt7286456",
        originalLanguage = "en",
        originalTitle = "Joker",
        posterPath = "/lbGzEyESjANpOeD48aROlc3X7aa.jpg",
        productionCompanies = listOf(),
        productionCountries = listOf(),
        releaseDate = "2019-10-02",
        spokenLanguages = listOf(Language("English", "en")),
        voteAverage = 8.1,
        voteCount = 129
    )

    val movieDomain = movieData.toDomain()
}
