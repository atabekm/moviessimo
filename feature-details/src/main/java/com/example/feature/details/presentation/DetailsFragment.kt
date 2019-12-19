package com.example.feature.details.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.ui.core.setContent
import com.example.core.network.model.Status
import com.example.feature.details.compose.MovieDetails
import com.example.feature.details.compose.MovieDetailsScreen
import com.example.feature.details.navigation.MovieDetailsNavigation
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailsFragment : Fragment() {
    private val viewModel: DetailsViewModel by viewModel()
    private val navigation: MovieDetailsNavigation by inject()
    private val movieState = MovieDetails()
    private var movieId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.apply {
            movieId = getInt("movie_id")
        }
        viewModel.getMovieDetails(movieId)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val group = FrameLayout(requireContext())
        group.setContent {
            MovieDetailsScreen(viewModel.movie) {
                navigation.goToMovieList()
            }
        }
        return group
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.movie.observe(viewLifecycleOwner, Observer { result ->
            when (result.status) {
                Status.LOADING -> {
                }
                Status.SUCCESS -> {
                    result.data?.apply {
                        movieState.movie = this
                    }
                }
                Status.ERROR -> {
                }
            }
        })
    }
}
