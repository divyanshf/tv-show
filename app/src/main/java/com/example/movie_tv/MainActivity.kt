package com.example.movie_tv

import android.app.Application
import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movie_tv.data.adapter.MovieAdapter
import com.example.movie_tv.data.model.Movie
import com.example.movie_tv.data.model.User
import com.example.movie_tv.data.viewmodel.MovieViewModel
import com.example.movie_tv.data.viewmodel.UserViewModel

class MainActivity : AppCompatActivity(), MovieAdapter.OnItemClickListener {
    private lateinit var movieViewModel: MovieViewModel
    private lateinit var userViewModel: UserViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var movieAdapter: MovieAdapter
    private lateinit var movies: List<Movie>

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
    private fun addToWatched(position: Int){
        var movie: Movie = movies[position]
        movie.watching = false
        movie.watched = true
        movieViewModel.update(movie)
    }

    fun addingMovie(view: View)
    {
        var intent: Intent = Intent(this, AddMovie::class.java)
        startActivity(intent)
    }

    override fun onItemClick(position: Int, view: View?) {
        when(view?.tag){
            "wish" -> addToWishlist(position)
            "watching" -> addToWatching(position)
            "watched" -> addToWatched(position)
            else -> {
                Toast.makeText(this, "Something went wrong!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //  Initialize latentit
        recyclerView = findViewById(R.id.recycler_view)
        movieAdapter = MovieAdapter(this, this)
        movieViewModel = MovieViewModel(application)
        userViewModel = UserViewModel(application)

        //  Check if the user is logged in
        var user:User? = userViewModel.getUser()
        if(user != null){
            if(!user?.isLogged){
                var intent: Intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }
            else{
                //  Recycler view setup
                recyclerView.adapter = movieAdapter
                recyclerView.layoutManager = LinearLayoutManager(this)

                //  Movie View Model
                movieViewModel.getAllMovies().observe(this, Observer {
                    movies = it
                    movieAdapter.setMovies(it)
                })
            }
        }
    }

    override fun onResume() {
        super.onResume()
        recyclerView.adapter = movieAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        movieViewModel.getAllMovies().observe(this, Observer {
            movies = it
            movieAdapter.setMovies(it)
        })
    }
}