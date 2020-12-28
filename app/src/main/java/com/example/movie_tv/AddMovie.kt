package com.example.movie_tv

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.EditText
import android.widget.RatingBar
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.movie_tv.data.model.Movie
import com.example.movie_tv.data.viewmodel.MovieViewModel
import com.google.android.material.textfield.TextInputEditText

class AddMovie : AppCompatActivity() {
    private lateinit var movieViewModel: MovieViewModel
    private lateinit var mname : TextInputEditText
    private lateinit var url : TextInputEditText
    private lateinit var ratingBar: RatingBar
    private var ratval: Int =0


    fun add(view: View)
    {
        val moviename =  mname.text.toString()
        val urlname= url.text.toString()

        if(inputCheck(moviename,urlname))
        {
            //Create user object
            val movie = Movie(0,moviename,urlname,ratval,false,false,false)
            //Add data to database
            movieViewModel.insert(movie)
            Toast.makeText(this, "Successfully Added!", Toast.LENGTH_LONG).show()

            var intent: Intent = Intent(this, MainActivity::class.java)
            startActivity(intent)

        }
        else
        {
             Toast.makeText(this, "Fill All the value", Toast.LENGTH_LONG).show()
        }

    }


    private fun inputCheck(moviename:String , url : String ):Boolean{
        return !(TextUtils.isEmpty(moviename) && TextUtils.isEmpty(url) )



    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_movie)

        mname= findViewById(R.id.mname_edt) as TextInputEditText
        url = findViewById(R.id.url_edt) as TextInputEditText
        ratingBar =findViewById(R.id.ratingBar) as RatingBar
        var txt : String
        ratingBar.onRatingBarChangeListener =
            RatingBar.OnRatingBarChangeListener { _, rating, _ ->

                if(rating.toInt()==1)
                    txt= "Worst "
                else if(rating.toInt()==2)
                    txt= "Poor "
                else if(rating.toInt()==3)
                    txt= "OK "
                else if(rating.toInt()==4)
                    txt= "Good "
                else
                    txt="Excellent "



                Toast.makeText(this, txt+"Movie", Toast.LENGTH_SHORT).show()

                ratval=rating.toInt()

            }


        movieViewModel = ViewModelProvider(this).get(MovieViewModel::class.java)



    }

}