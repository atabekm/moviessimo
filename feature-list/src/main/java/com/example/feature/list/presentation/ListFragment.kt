package com.example.feature.list.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.core.network.model.Status
import com.example.feature.list.R
import com.example.feature.list.presentation.adapter.MovieAdapter
import kotlinx.android.synthetic.main.fragment_list.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ListFragment : Fragment() {
    private val listViewModel: ListViewModel by viewModel()
    private val adapter = MovieAdapter()

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
        listViewModel.discoverMovieLiveData.observe(viewLifecycleOwner, Observer { result ->
            when (result.status) {
                Status.LOADING -> {}
                Status.SUCCESS -> {
                    adapter.submitList(result.data?.results)
                }
                Status.ERROR -> {}
            }
        })
    }
}