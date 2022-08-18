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
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class ListFragment : Fragment() {
    private val listViewModel: ListViewModel by viewModel()
    private val navigation: MovieListNavigation by inject()
    private val adapter = MovieAdapter(navigation)

    private var _binding: FragmentListBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding
        get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        listViewModel.requestMovies()
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
        listViewModel.movies.observe(viewLifecycleOwner) { result ->
            when (result.status) {
                Status.LOADING -> {
                    binding.movieProgress.isVisible = true
                }
                Status.SUCCESS -> {
                    binding.movieProgress.isVisible = false
                    adapter.submitList(result.data)
                }
                Status.ERROR -> {
                    binding.movieProgress.isVisible = false
                    Snackbar.make(binding.movieRecycler, result.message, Snackbar.LENGTH_INDEFINITE)
                        .setAction(getString(R.string.retry)) { listViewModel.requestMovies() }
                        .show()
                }
            }
        }
    }
}
