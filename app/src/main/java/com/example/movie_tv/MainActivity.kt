package com.example.movie_tv

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel:MovieViewModel
    private lateinit var recyclerView:RecyclerView
    private lateinit var movieAdapter:MovieAdapter

    fun addToWishlist(view: View){
    }
    fun addToWatching(view: View){
    }
    fun addToWatched(view: View){
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //  Recycler view setup
        recyclerView = findViewById(R.id.recycler_view)
        movieAdapter = MovieAdapter(this)
        recyclerView.adapter = movieAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        //  View model
        viewModel = MovieViewModel(application)

        viewModel.getAllMovies().observe(this, Observer {
            movieAdapter.setMovies(it)
        })

    }
}