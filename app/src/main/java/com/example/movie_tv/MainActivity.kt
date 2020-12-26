package com.example.movie_tv

import android.app.Application
import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movie_tv.data.adapter.MovieAdapter
import com.example.movie_tv.data.model.User
import com.example.movie_tv.data.viewmodel.MovieViewModel
import com.example.movie_tv.data.viewmodel.UserViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var movieViewModel: MovieViewModel
    private lateinit var userViewModel: UserViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var movieAdapter: MovieAdapter

    fun addToWishlist(view: View){
    }
    fun addToWatching(view: View){
    }
    fun addToWatched(view: View){
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //  Initialize latentit
        recyclerView = findViewById(R.id.recycler_view)
        movieAdapter = MovieAdapter(this)
        movieViewModel = MovieViewModel(application)
        userViewModel = UserViewModel(application)

        //  Check if the user is logged in
        var user:User = userViewModel.getUser()
        Log.i("ACTIVITY", user.isLogged.toString())
        if(!user.isLogged){
            var intent: Intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
        else{
            //  Recycler view setup
            recyclerView.adapter = movieAdapter
            recyclerView.layoutManager = LinearLayoutManager(this)

            //  Movie View Model
            movieViewModel.getAllMovies().observe(this, Observer {
                movieAdapter.setMovies(it)
            })
        }
    }

    override fun onResume() {
        super.onResume()
        recyclerView.adapter = movieAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        movieViewModel.getAllMovies().observe(this, Observer {
            movieAdapter.setMovies(it)
        })
    }
}