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
import coil.api.load
import com.example.core.network.model.Status
import com.example.feature.details.databinding.FragmentDetailsBinding
import com.example.feature.details.R
import com.example.feature.details.navigation.MovieDetailsNavigation
import com.google.android.material.appbar.AppBarLayout
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.math.abs

class DetailsFragment : Fragment(), AppBarLayout.OnOffsetChangedListener {
    private val viewModel: DetailsViewModel by viewModel()
    private val navigation: MovieDetailsNavigation by inject()
    private var movieId = 0
    private var isPosterShown = true
    private var maxScrollSize = 0.0
    private var disposables: CompositeDisposable = CompositeDisposable()

    private var _binding: FragmentDetailsBinding? = null
    // This property is only valid between onCreateView and onDestroyView.
    private val binding
        get() = _binding!!

    companion object {
        const val POSTER_ANIMATION_DURATION = 200L
        const val RATIO_TO_ANIMATE_POSTER = 0.75
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activity?.window?.statusBarColor = Color.TRANSPARENT

        disposables.add(
            viewModel
                .viewState
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(::render) { println("something went terribly wrong processing view state $it") }
        )

        disposables.add(
            viewModel
                .viewEffect
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(::trigger) { println("something went terribly wrong processing view effects $it") }
        )

        arguments?.apply {
            movieId = getInt("movie_id")
        }
        viewModel.processInput(DetailViewEvent.MovieLoadEvent(movieId))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // This if condition is only to avoid the block to be executed in instrumentation tests
        if (activity is AppCompatActivity) {
            (activity as AppCompatActivity).apply {
                setSupportActionBar(binding.detailToolbar)
                supportActionBar?.setDisplayHomeAsUpEnabled(true)
            }
        }

        binding.detailToolbar.setNavigationOnClickListener {
            viewModel.processInput(DetailViewEvent.MovieBackClickEvent)
        }
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

    private fun render(viewState: DetailViewState) {
        with(binding) {
            detailProgress.isVisible = viewState.isLoading
            detailCollapsingToolbar.title = viewState.movie.title
            detailBackdrop.load(viewState.movie.backdropImage)
            detailPoster.load(viewState.movie.posterImage)
            detailGenres.text = viewState.movie.genres
            detailDuration.text = viewState.movie.duration
            detailRating.rating = viewState.movie.rating
            detailOverview.text = viewState.movie.overview
            detailDirector.text = viewState.movie.director
            detailScreenplayCaption.isVisible = viewState.movie.screenplay.isNotEmpty()
            detailScreenplay.text = viewState.movie.screenplay
            detailCasting.text = viewState.movie.cast
            detailRoot.isInvisible = viewState.isLoading
        }
    }

    private fun trigger(effect: DetailViewEffect?) {
        effect ?: return
        when (effect) {
            is DetailViewEffect.MovieBackClickEffect -> {
                navigation.goToMovieList()
            }
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
