package com.example.movie_tv.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.movie_tv.R
import com.example.movie_tv.data.adapter.MovieAdapter
import com.example.movie_tv.data.viewmodel.MovieViewModel
import com.example.movie_tv.data.viewmodel.UserViewModel


class Fragment_Wish : Fragment() {
    private lateinit var movieViewModel: MovieViewModel

    private lateinit var recyclerView: RecyclerView
    private lateinit var movieAdapter: MovieAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        

        }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment__wish, container, false)
    }


}