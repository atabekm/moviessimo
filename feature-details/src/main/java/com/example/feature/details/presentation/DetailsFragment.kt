package com.example.feature.details.presentation

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import coil.api.load
import com.example.core.network.model.Status
import com.example.feature.details.R
import com.example.feature.details.navigation.MovieDetailsNavigation
import com.google.android.material.appbar.AppBarLayout
import kotlinx.android.synthetic.main.fragment_details.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.math.abs

class DetailsFragment : Fragment(R.layout.fragment_details), AppBarLayout.OnOffsetChangedListener {
    private val viewModel: DetailsViewModel by viewModel()
    private val navigation: MovieDetailsNavigation by inject()
    private var movieId = 0
    private var isPosterShown = true
    private var maxScrollSize = 0.0

    companion object {
        const val POSTER_ANIMATION_DURATION = 200L
        const val RATIO_TO_ANIMATE_POSTER = 0.75
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activity?.window?.statusBarColor = Color.TRANSPARENT

        arguments?.apply {
            movieId = getInt("movie_id")
        }
        viewModel.getMovieDetails(movieId)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // This if condition is only to avoid the block to executed in instrumentation tests
        if (activity is AppCompatActivity) {
            (activity as AppCompatActivity).apply {
                setSupportActionBar(detailToolbar)
                supportActionBar?.setDisplayHomeAsUpEnabled(true)
            }
        }

        detailToolbar.setNavigationOnClickListener {
            navigation.goToMovieList()
        }

        viewModel.movie.observe(viewLifecycleOwner, Observer { result ->
            when (result.status) {
                Status.LOADING -> {
                    detailRoot.isInvisible = true
                    detailProgress.isVisible = true
                }
                Status.SUCCESS -> {
                    result.data?.apply {
                        detailProgress.isVisible = false
                        detailCollapsingToolbar.title = title

                        detailBackdrop.load(backdropImage)
                        detailPoster.load(posterImage)
                        detailGenres.text = genres
                        detailDuration.text = duration
                        detailRating.rating = rating
                        detailOverview.text = overview
                        detailDirector.text = director
                        detailScreenplayCaption.isVisible = screenplay.isNotEmpty()
                        detailScreenplay.text = screenplay
                        detailCasting.text = cast
                    }
                    detailRoot.isInvisible = false
                }
                Status.ERROR -> {
                    detailProgress.isVisible = false
                    detailRoot.isInvisible = true
                }
            }
        })
    }

    override fun onResume() {
        super.onResume()
        detailAppbar.addOnOffsetChangedListener(this)
    }

    override fun onPause() {
        detailAppbar.removeOnOffsetChangedListener(this)
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
