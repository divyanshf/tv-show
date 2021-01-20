package com.example.movie_tv.fragments

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.movie_tv.R
import com.example.movie_tv.data.model.Movie
import com.example.movie_tv.data.remote.MovieFirestore
import com.example.movie_tv.data.viewmodel.MovieViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class FragmentsAdd : Fragment() {
    private lateinit var movieViewModel: MovieViewModel
    private lateinit var mnameView : TextInputEditText
    private lateinit var urlView : TextInputEditText
    private lateinit var yearView : TextInputEditText
    private lateinit var ratingBar : RatingBar
    private lateinit var addButton : FloatingActionButton
    private var ratval: Long =0

    fun add(view: View)
    {
        try {
            val movieName =  mnameView.text.toString()
            val urlName = urlView.text.toString()
            val yearval = yearView.text.toString().toLong()

            if(inputCheck(movieName, urlName, yearval))
            {
                //Create user object
                val movie = Movie(movieName , yearval, urlName, ratval,
                    wishList = true,
                    watching = false,
                    watched = false
                )
//                Add data to database
                try {
                    movieViewModel.insert(movie)
                }catch (e:Exception){
                    e.printStackTrace()
                }

                mnameView.setText("")
                urlView.setText("")
                yearView.setText("")
                ratingBar.rating = 0F
                Toast.makeText(context, "Successfully Added!", Toast.LENGTH_LONG).show()
            }
        }catch (e:Exception){
            Toast.makeText(context, "Invalid value!", Toast.LENGTH_LONG).show()
        }
    }

    private fun inputCheck(moviename:String , url : String, year: Long):Boolean{
        if(TextUtils.isEmpty(moviename)){
            return false
        }
        else if(TextUtils.isEmpty(url)){
            return false
        }
        else if(year > 2020){
            return false
        }
        return true
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add, container, false)

        mnameView = view.findViewById(R.id.mname_edt)
        urlView = view.findViewById(R.id.url_edt)
        yearView = view.findViewById(R.id.year_edt)
        ratingBar = view.findViewById(R.id.ratingBar)
        addButton = view.findViewById(R.id.floatingActionButton2)

        addButton.setOnClickListener {
            add(it)
        }

        var txt : String
        ratingBar.onRatingBarChangeListener =
            RatingBar.OnRatingBarChangeListener { _, rating, _ ->

                txt = when(rating.toInt()){
                    1 -> "Worst Movie"
                    2 -> "Poor Movie"
                    3 -> "OK Movie"
                    4 -> "Good Movie"
                    5 -> "Excellent Movie"
                    else -> ""
                }

                if(txt.isNotEmpty()){
                    Toast.makeText(context, txt, Toast.LENGTH_SHORT).show()
                }
                ratval=rating.toLong()
            }

        movieViewModel = ViewModelProvider(this).get(MovieViewModel::class.java)

        return view
    }
}