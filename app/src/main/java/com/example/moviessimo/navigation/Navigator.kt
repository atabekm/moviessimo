package com.example.moviessimo.navigation

import android.os.Bundle
import androidx.navigation.NavController
import com.example.feature.list.navigation.MovieDetailsNavigation
import com.example.moviessimo.R

class Navigator : MovieDetailsNavigation {
    private var navController: NavController? = null

    fun bind(navController: NavController) {
        this.navController = navController
    }

    fun unbind() {
        navController = null
    }

    override fun openMovie(id: Int) {
        val bundle = Bundle()
        bundle.putInt("movie_id", id)
        navController?.navigate(R.id.actionMovieDetails, bundle)
    }
}
