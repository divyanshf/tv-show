package com.example.movie_tv

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat.startActivity

class my_list : AppCompatActivity() {


 
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_list)
    }
    fun toMymovies(view: View) {
        var intent: Intent = Intent(this, movieTabs::class.java)
        startActivity(intent)}



    fun toMain(view: View) {
        var intent: Intent = Intent(this, MainActivity::class.java)
        startActivity(intent)}
}