package com.example.movie_tv.fragments

import android.os.Bundle
import android.util.Log
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


class FragmentWish : Fragment(), MovieAdapter.OnItemClickListener {
    private lateinit var movieViewModel: MovieViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var movieAdapter: MovieAdapter
    private lateinit var movies: List<Movie>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment__wish, container, false)

        recyclerView = view.findViewById(R.id.recycler_view_wish_list)
        movieAdapter = MovieAdapter(requireContext(), this)
        movieViewModel = MovieViewModel(activity?.application!!)

        recyclerView.adapter = movieAdapter
        recyclerView.layoutManager = LinearLayoutManager(context)

        movieViewModel.getWishMovies().observe(viewLifecycleOwner, Observer {
            movies = it
            movieAdapter.setMovies(movies)
        })

        return view
    }

    override fun onItemClick(position: Int, view: View?) {
        when(view?.tag){
            "wish" -> addToWishlist(position)
            "watching" -> addToWatching(position)
            "watched" -> addToWatched(position)
            "delete" -> deleteMovie(position)
            else -> {
                Toast.makeText(context, "Something went wrong!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun deleteMovie(position: Int){
        var movie:Movie = movies[position]
        movieViewModel.delete(movie)
    }

    private fun addToWishlist(position: Int){
        var movie: Movie = movies[position]
        movie.wishList = !movie.wishList
        movieViewModel.update(movie)
        if(!movie.wishList and !movie.watched and !movie.watching){
            movieViewModel.delete(movie)
        }
    }

    private fun addToWatching(position: Int){
        var movie: Movie = movies[position]
        movie.watching = true
        movie.watched = false
        movieViewModel.update(movie)
        if(!movie.wishList and !movie.watched and !movie.watching){
            movieViewModel.delete(movie)
        }
    }

    private fun addToWatched(position: Int){
        var movie: Movie = movies[position]
        movie.watching = false
        movie.watched = true
        movieViewModel.update(movie)
        if(!movie.wishList and !movie.watched and !movie.watching){
            movieViewModel.delete(movie)
        }
    }
}