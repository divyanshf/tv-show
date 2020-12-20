package com.example.movie_tv.movie

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie_table")
data class MovieModel(
    @PrimaryKey val movieId:Int,
    val movieName:String,
    val movieRating:Float
)