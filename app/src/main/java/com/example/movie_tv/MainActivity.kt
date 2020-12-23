package com.example.movie_tv

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var recyclerView:RecyclerView = findViewById(R.id.recycler_view)
        var movieAdapter:MovieAdapter = MovieAdapter(this)
        recyclerView.adapter = movieAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }
}