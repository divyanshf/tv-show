package com.example.movie_tv.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movie_tv.R
import com.example.movie_tv.data.adapter.MovieAdapter
import com.example.movie_tv.data.model.Movie
import com.example.movie_tv.data.viewmodel.MovieViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentsWatched : Fragment(), MovieAdapter.OnItemClickListener {
    private val movieViewModel: MovieViewModel by viewModels()
    private lateinit var recyclerView: RecyclerView
    private lateinit var movieAdapter: MovieAdapter
    private lateinit var movies: List<Movie>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_watched, container, false)

        //  Initialize the lateinits
        recyclerView = view.findViewById(R.id.recycler_view_watched)
        movieAdapter = MovieAdapter(requireContext(), true, this)

        recyclerView.adapter = movieAdapter
        recyclerView.layoutManager = LinearLayoutManager(context)

        //  Observe the watched movies
        movieViewModel.getWatchedMovies().observe(viewLifecycleOwner, {
            movies = it
            movieAdapter.setMovies(movies)
        })

        return view
    }

    //  On item clicks
    override fun onItemClick(position: Int, view: View?) {
        when(view?.tag){
            "wish" -> addToWishlist(position)
            "watching" -> addToWatching(position)
            "delete" -> deleteMovie(position)
            else -> {
                Toast.makeText(context, "Something went wrong!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun deleteMovie(position: Int){
        val movie:Movie = movies[position]
        movieViewModel.delete(movie)
    }

    private fun addToWishlist(position: Int){
        val movie: Movie = movies[position]
        movie.wishList = !movie.wishList
        movieViewModel.update(movie)
        if(!movie.wishList and !movie.watched and !movie.watching){
            movieViewModel.delete(movie)
        }
    }

    private fun addToWatching(position: Int){
        val movie: Movie = movies[position]
        movie.watching = true
        movie.watched = false
        movieViewModel.update(movie)
        if(!movie.wishList and !movie.watched and !movie.watching){
            movieViewModel.delete(movie)
        }
    }
}