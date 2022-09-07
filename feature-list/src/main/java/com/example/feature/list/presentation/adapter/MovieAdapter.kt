package com.example.feature.list.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.feature.list.R
import com.example.feature.list.domain.model.MoviePoster

internal class MovieAdapter(
    private val openMovieCallback: (Int) -> Unit
) : ListAdapter<MoviePoster, MovieAdapter.ViewHolder>(MovieDiffCallback()) {

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
        holder.bind(getItem(position), openMovieCallback)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(movie: MoviePoster, openMovieCallback: (Int) -> Unit) {
            itemView.setOnClickListener {
                openMovieCallback(movie.id)
            }
            itemView.findViewById<ImageView>(R.id.itemMovieImage).apply {
                load(movie.posterImageUrl)
                contentDescription = movie.title
                tag = movie.posterImageUrl
            }
        }
    }
}

internal class MovieDiffCallback : DiffUtil.ItemCallback<MoviePoster>() {

    override fun areItemsTheSame(oldItem: MoviePoster, newItem: MoviePoster): Boolean {
        return oldItem.hashCode() == newItem.hashCode()
    }

    override fun areContentsTheSame(oldItem: MoviePoster, newItem: MoviePoster): Boolean {
        return oldItem == newItem
    }
}
