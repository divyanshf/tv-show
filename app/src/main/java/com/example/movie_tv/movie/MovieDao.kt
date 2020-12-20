package com.example.movie_tv.movie

import androidx.room.*
import com.example.movie_tv.login.UserModel

@Dao
interface MovieDao {
    @Insert
    fun insert(movie: MovieModel)

    @Update
    fun update(movie: MovieModel)

    @Delete
    fun delete(movie: MovieModel)

    @Query("SELECT * FROM movie_table WHERE movie_table.movieId = movieId LIMIT 1")
    fun getMovie(movieId:Int)
}