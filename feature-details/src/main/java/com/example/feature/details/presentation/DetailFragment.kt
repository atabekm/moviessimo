package com.example.feature.details.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import coil.api.load
import com.example.core.network.model.Status
import com.example.feature.details.R
import com.google.android.material.appbar.AppBarLayout
import kotlinx.android.synthetic.main.fragment_details.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.math.abs

class DetailFragment : Fragment(), AppBarLayout.OnOffsetChangedListener {
    val viewModel: DetailViewModel by viewModel()
    private var movieId = 0
    private var isPosterShown = true
    private var maxScrollSize = 0.0

    companion object {
        const val POSTER_ANIMATION_DURATION = 200L
        const val RATIO_TO_ANIMATE_POSTER = 0.75
    }

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
                    collapsing_toolbar.title = it.data?.title

                    val urlBackdrop = "https://image.tmdb.org/t/p/w500${it.data?.backdropPath}"
                    detailBackdrop.load(urlBackdrop)

                    val urlPoster = "https://image.tmdb.org/t/p/w500${it.data?.posterPath}"
                    detailPoster.load(urlPoster)
                }
                Status.ERROR -> {
                    detailProgress.isVisible = false
                }
            }
        })
    }

    override fun onResume() {
        super.onResume()
        appbar.addOnOffsetChangedListener(this)
    }

    override fun onPause() {
        appbar.removeOnOffsetChangedListener(this)
        super.onPause()
    }

    // based on https://github.com/saulmm/CoordinatorExamples/blob/master/app/src/main/java/saulmm/coordinatorexamples/MaterialUpConceptActivity.java
    override fun onOffsetChanged(appBarLayout: AppBarLayout, i: Int) {
        if (maxScrollSize == 0.0) maxScrollSize = appBarLayout.totalScrollRange.toDouble()

        val scrollRatio = abs(i) / maxScrollSize

        if (scrollRatio >= RATIO_TO_ANIMATE_POSTER && isPosterShown) {
            isPosterShown = false
            ViewCompat.animate(detailPoster)
            animateView(detailPoster, false)
        } else if (scrollRatio <= RATIO_TO_ANIMATE_POSTER && !isPosterShown) {
            isPosterShown = true
            animateView(detailPoster, true)
        }
    }

    private fun animateView(view: View, isVisible: Boolean) {
        view.animate()
            .scaleY(if (isVisible) 1f else 0f)
            .scaleX(if (isVisible) 1f else 0f)
            .alpha(if (isVisible) 1f else 0f)
            .setDuration(POSTER_ANIMATION_DURATION)
            .start()
    }
}
