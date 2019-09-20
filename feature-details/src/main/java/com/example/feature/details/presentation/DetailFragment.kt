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

class DetailFragment : Fragment(), AppBarLayout.OnOffsetChangedListener {
    private val viewModel: DetailViewModel by viewModel()
    private val navigation: MovieDetailsNavigation by inject()
    private var movieId = 0
    private var isPosterShown = true
    private var maxScrollSize = 0.0

    companion object {
        const val POSTER_ANIMATION_DURATION = 200L
        const val RATIO_TO_ANIMATE_POSTER = 0.75
        const val CAST_THRESHOLD = 5
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activity?.window?.statusBarColor = Color.TRANSPARENT

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

        (activity as AppCompatActivity).apply {
            setSupportActionBar(toolbar)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            toolbar.setNavigationOnClickListener {
                navigation.goToMovieList()
            }
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
                        collapsing_toolbar.title = title

                        val urlBackdrop = "https://image.tmdb.org/t/p/w500$backdropPath"
                        detailBackdrop.load(urlBackdrop)

                        val urlPoster = "https://image.tmdb.org/t/p/w500$posterPath"
                        detailPoster.load(urlPoster)
                        detailGenres.text = genres?.joinToString {
                            it.name ?: ""
                        } ?: ""
                        detailDuration.text = "$runtime minutes"
                        voteAverage?.let {
                            detailRating.rating = it.div(2).toFloat()
                        }
                        detailOverview.text = overview
                        detailCasting.text = credits?.cast?.take(CAST_THRESHOLD)?.joinToString {
                            it.name
                        }
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
