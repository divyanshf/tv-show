package com.example.movie_tv

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MyList : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_list)
    }

    fun toMyMovies(view: View) {
        var intent: Intent = Intent(this, MovieTabs::class.java)
        startActivity(intent)
    }

    fun toMain(view: View) {
        var intent: Intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}