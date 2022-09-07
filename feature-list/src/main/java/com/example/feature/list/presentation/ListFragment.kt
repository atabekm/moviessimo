package com.example.feature.list.presentation

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.example.core.utils.viewBinding
import com.example.feature.list.R
import com.example.feature.list.databinding.FragmentListBinding
import com.example.feature.list.navigation.MovieListNavigation
import com.example.feature.list.presentation.adapter.MovieAdapter
import com.google.android.material.snackbar.Snackbar
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.orbitmvi.orbit.viewmodel.observe

class ListFragment : Fragment(R.layout.fragment_list) {
    private val viewModel: ListViewModel by viewModel()
    private val navigation: MovieListNavigation by inject()
    private val adapter by lazy { MovieAdapter(navigation::openMovie) }
    private val binding by viewBinding(FragmentListBinding::bind)
    private var snackbar: Snackbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.dispatch(ListAction.LoadMoviesAction)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.window?.statusBarColor =
            ContextCompat.getColor(requireContext(), R.color.colorPrimaryDark)

        binding.movieRecycler.adapter = adapter

        viewModel.observe(viewLifecycleOwner, ::render, ::handleSideEffects)
    }

    private fun render(state: ListState) {
        binding.movieProgress.isVisible = state.isLoading
        adapter.submitList(state.moviePosters)
    }

    private fun handleSideEffects(effect: ListEffect) {
        when (effect) {
            is ListEffect.ListErrorEffect -> {
                snackbar = Snackbar.make(
                    binding.movieRecycler,
                    effect.message,
                    Snackbar.LENGTH_INDEFINITE
                )
                    .setAction(getString(R.string.retry)) {
                        viewModel.dispatch(ListAction.LoadMoviesAction)
                    }
                snackbar?.show()
            }
        }
    }
}
