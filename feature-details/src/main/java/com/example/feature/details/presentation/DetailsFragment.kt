package com.example.feature.details.presentation

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import coil.load
import com.example.core.utils.viewBinding
import com.example.feature.details.R
import com.example.feature.details.databinding.FragmentDetailsBinding
import com.example.feature.details.navigation.MovieDetailsNavigation
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.snackbar.Snackbar
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.orbitmvi.orbit.viewmodel.observe
import kotlin.math.abs

class DetailsFragment : Fragment(R.layout.fragment_details), AppBarLayout.OnOffsetChangedListener {
    private val viewModel: DetailsViewModel by viewModel()
    private val navigation: MovieDetailsNavigation by inject()
    private val binding by viewBinding(FragmentDetailsBinding::bind)
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
        openMovieDetails()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // This if condition is only to avoid the block to executed in instrumentation tests
        if (activity is AppCompatActivity) {
            (activity as AppCompatActivity).apply {
                setSupportActionBar(binding.detailToolbar)
                supportActionBar?.setDisplayHomeAsUpEnabled(true)
            }
        }

        binding.detailToolbar.setNavigationOnClickListener {
            navigation.goToMovieList()
        }

        viewModel.observe(viewLifecycleOwner, ::render, ::handleSideEffects)
    }

    override fun onResume() {
        super.onResume()
        binding.detailAppbar.addOnOffsetChangedListener(this)
    }

    override fun onPause() {
        binding.detailAppbar.removeOnOffsetChangedListener(this)
        super.onPause()
    }

    // based on https://github.com/saulmm/CoordinatorExamples/blob/master/app/src/main/java/saulmm/coordinatorexamples/MaterialUpConceptActivity.java
    override fun onOffsetChanged(appBarLayout: AppBarLayout, i: Int) {
        if (maxScrollSize == 0.0) maxScrollSize = appBarLayout.totalScrollRange.toDouble()

        val scrollRatio = abs(i) / maxScrollSize

        if (scrollRatio >= RATIO_TO_ANIMATE_POSTER && isPosterShown) {
            isPosterShown = false
            ViewCompat.animate(binding.detailPoster)
            animateView(binding.detailPoster, false)
        } else if (scrollRatio <= RATIO_TO_ANIMATE_POSTER && !isPosterShown) {
            isPosterShown = true
            animateView(binding.detailPoster, true)
        }
    }

    private fun render(state: DetailsState) {
        binding.detailRoot.isInvisible = true
        binding.detailProgress.isVisible = state.isLoading
        state.movie?.apply {
            binding.detailCollapsingToolbar.title = title
            binding.detailBackdrop.load(backdropImage)
            binding.detailPoster.load(posterImage)
            binding.detailGenres.text = genres
            binding.detailDuration.text = duration
            binding.detailRating.rating = rating
            binding.detailOverview.text = overview
            binding.detailDirector.text = director
            binding.detailScreenplayCaption.isVisible = screenplay.isNotEmpty()
            binding.detailScreenplay.text = screenplay
            binding.detailCasting.text = cast
            binding.detailRoot.isVisible = true
        }
    }

    private fun handleSideEffects(effect: DetailsEffect) {
        when (effect) {
            is DetailsEffect.DetailsErrorEffect -> {
                Snackbar.make(binding.detailRoot, effect.message, Snackbar.LENGTH_INDEFINITE)
                    .setAction(getString(R.string.retry)) {
                        openMovieDetails()
                    }
                    .show()
            }
        }
    }

    private fun openMovieDetails() {
        viewModel.dispatch(DetailsAction.OpenMovieDetailsAction(movieId))
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
