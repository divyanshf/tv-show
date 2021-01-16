package com.example.movie_tv.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movie_tv.R
import com.example.movie_tv.data.adapter.MovieAdapter
import com.example.movie_tv.data.model.Movie
import com.example.movie_tv.data.viewmodel.MovieViewModel


class FragmentsWatched : Fragment(), MovieAdapter.OnItemClickListener {
    private lateinit var movieViewModel: MovieViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var movieAdapter: MovieAdapter
    private lateinit var movies: List<Movie>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_watched, container, false)

        recyclerView = view.findViewById(R.id.recycler_view_watched)
        movieAdapter = MovieAdapter(requireContext(), this)
        movieViewModel = MovieViewModel(activity?.application!!)

        recyclerView.adapter = movieAdapter
        recyclerView.layoutManager = LinearLayoutManager(context)

        movieViewModel.getWatchedMovies().observe(viewLifecycleOwner, Observer {
            movies = it
            movieAdapter.setMovies(movies)
        })

        return view
    }

    override fun onItemClick(position: Int, view: View?) {
        when(view?.tag){
            "wish" -> addToWishlist(position)
            "watching" -> addToWatching(position)
            else -> {
                Toast.makeText(context, "Something went wrong!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun addToWishlist(position: Int){
        var movie: Movie = movies[position]
        movie.wishList = !movie.wishList
        movieViewModel.update(movie)
    }

    private fun addToWatching(position: Int){
        var movie: Movie = movies[position]
        movie.watching = true
        movie.watched = false
        movieViewModel.update(movie)
    }
}