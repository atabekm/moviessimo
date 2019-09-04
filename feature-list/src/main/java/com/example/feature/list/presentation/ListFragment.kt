package com.example.feature.list.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.core.network.model.Status
import com.example.feature.list.R
import com.example.feature.list.presentation.adapter.MovieAdapter
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_list.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ListFragment : Fragment() {
    private val listViewModel: ListViewModel by viewModel()
    private val adapter = MovieAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        listViewModel.requestMovies()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        movieRecycler.adapter = adapter
        listViewModel.movies.observe(viewLifecycleOwner, Observer { result ->
            when (result.status) {
                Status.LOADING -> {
                    movieProgress.isVisible = true
                }
                Status.SUCCESS -> {
                    movieProgress.isVisible = false
                    adapter.submitList(result.data?.results)
                }
                Status.ERROR -> {
                    movieProgress.isVisible = false
                    Snackbar.make(movieRecycler, result.message, Snackbar.LENGTH_INDEFINITE)
                        .setAction(getString(R.string.retry)) {
                            listViewModel.requestMovies()
                        }.show()
                }
            }
        })
    }
}