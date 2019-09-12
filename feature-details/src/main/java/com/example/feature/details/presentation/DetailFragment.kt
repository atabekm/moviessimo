package com.example.feature.details.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import coil.api.load
import com.example.core.network.model.Status
import com.example.feature.details.R
import kotlinx.android.synthetic.main.fragment_details.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailFragment : Fragment() {
    val viewModel: DetailViewModel by viewModel()
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
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.movie.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.LOADING -> {
                    detailProgress.isVisible = true
                }
                Status.SUCCESS -> {
                    detailProgress.isVisible = false
                    detailText.text = it.data?.title

                    val url = "https://image.tmdb.org/t/p/w500${it.data?.backdropPath}"
                    detailBackdrop.load(url)
                }
                Status.ERROR -> {
                    detailProgress.isVisible = false
                }
            }
        })
    }
}
