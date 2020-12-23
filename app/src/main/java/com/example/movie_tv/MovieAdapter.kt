package com.example.movie_tv

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MovieAdapter(context: Context) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {
    private val mInflater : LayoutInflater = LayoutInflater.from(context)
    private var mMovies : List<Movie> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieAdapter.MovieViewHolder {
        var itemView:View = mInflater.inflate(R.layout.movie_item, parent, false)
        return MovieViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return mMovies.size
    }

    override fun onBindViewHolder(holder: MovieAdapter.MovieViewHolder, position: Int) {
        var currentMovie:Movie = mMovies[position]
        holder.titleView.text = currentMovie.movieName
        holder.ratingView.text = currentMovie.movieRating.toString()
    }

    fun setMovies(movies : List<Movie>){
        mMovies = movies
        notifyDataSetChanged()
    }

    class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var titleView:TextView = itemView.findViewById(R.id.title_view)
        var ratingView:TextView = itemView.findViewById(R.id.rating_view)
    }
}