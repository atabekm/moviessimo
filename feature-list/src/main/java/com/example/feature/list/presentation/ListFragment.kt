package com.example.feature.list.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.example.core.network.model.Status
import com.example.feature.list.R
import com.example.feature.list.databinding.FragmentListBinding
import com.example.feature.list.navigation.MovieListNavigation
import com.example.feature.list.presentation.adapter.MovieAdapter
import com.google.android.material.snackbar.Snackbar
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class ListFragment : Fragment() {
    private val listViewModel: ListViewModel by viewModel()
    private val navigation: MovieListNavigation by inject()
    private val adapter = MovieAdapter {
        viewModel.processInput(ListViewEvent.MovieClickEvent(it))
    }

    private var disposables: CompositeDisposable = CompositeDisposable()

    private var _binding: FragmentListBinding? = null
    // This property is only valid between onCreateView and onDestroyView.
    private val binding
        get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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

        viewModel.processInput(ListViewEvent.MovieLoadEvent)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.window?.statusBarColor = ContextCompat.getColor(requireContext(), R.color.colorPrimaryDark)

        binding.movieRecycler.adapter = adapter
    }

    private fun render(viewState: ListViewState) {
        binding.movieProgress.isVisible = viewState.isLoading
        adapter.submitList(viewState.movieList)
        if (viewState.errorMessage.isNotEmpty()) {
            Snackbar.make(binding.movieRecycler, viewState.errorMessage, Snackbar.LENGTH_INDEFINITE)
                .setAction(getString(R.string.retry)) {
                    viewModel.processInput(ListViewEvent.MovieRetryEvent)
                }
                .show()
        }
    }

    private fun trigger(effect: ListViewEffect?) {
        effect ?: return
        when (effect) {
            is ListViewEffect.MovieClickEffect -> {
                navigation.openMovie(effect.movieId)
            }
        }
    }
}
