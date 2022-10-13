package com.example.moviessimo

sealed class Screen(val route: String) {
    object MovieList : Screen("movie_list")
    object MovieDetails : Screen("movie_details/{movie_id}") {
        fun createRoute(movieId: Int): String {
            return route.replace("{movie_id}", movieId.toString())
        }
    }
}
