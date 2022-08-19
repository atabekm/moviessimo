package com.example.feature.list.presentation

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.core.utils.viewBinding
import com.example.feature.list.R
import com.example.feature.list.databinding.FragmentListBinding
import com.example.feature.list.navigation.MovieListNavigation
import com.example.feature.list.presentation.adapter.MovieAdapter
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class ListFragment : Fragment(R.layout.fragment_list) {
    private val listViewModel: ListViewModel by viewModel()
    private val navigation: MovieListNavigation by inject()
    private val adapter by lazy { MovieAdapter(navigation::openMovie) }
    private val binding by viewBinding(FragmentListBinding::bind)
    private var snackbar: Snackbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        listViewModel.dispatch(ListAction.LoadMoviesAction)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.window?.statusBarColor =
            ContextCompat.getColor(requireContext(), R.color.colorPrimaryDark)

        binding.movieRecycler.adapter = adapter

        lifecycleScope.launchWhenCreated {
            listViewModel.observeState().filterNotNull()
                .onStart { emit(ListState(isLoading = true)) }
                .onEach { state ->
                    binding.movieProgress.isVisible = state.isLoading
                    adapter.submitList(state.movies)
                    if (state.error.isEmpty()) {
                        snackbar?.dismiss()
                    } else {
                        snackbar = Snackbar.make(binding.movieRecycler, state.error, Snackbar.LENGTH_INDEFINITE)
                            .setAction(getString(R.string.retry)) {
                                listViewModel.dispatch(ListAction.LoadMoviesAction)
                            }
                        snackbar?.show()
                    }
                }
                .launchIn(this)
        }
    }
}
