package com.example.feature.list.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.example.feature.list.R
import com.example.feature.list.domain.model.Movie
import com.example.feature.list.navigation.MovieListNavigation
import kotlinx.android.synthetic.main.item_movie.view.*

class MovieAdapter(
    private val navigation: MovieListNavigation
) : ListAdapter<Movie, MovieAdapter.ViewHolder>(MovieDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_movie,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), navigation)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(movie: Movie, navigation: MovieListNavigation) {
            itemView.setOnClickListener {
                navigation.openMovie(movie.id)
            }
            itemView.itemMovieImage.load(movie.posterImage)
        }
    }
}

class MovieDiffCallback : DiffUtil.ItemCallback<Movie>() {

    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem.hashCode() == newItem.hashCode()
    }

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem == newItem
    }
}
