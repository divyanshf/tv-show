package com.example.movie_tv.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.movie_tv.data.model.Movie

@Dao
interface MovieDao {
    @Insert
    fun insert(movie: Movie) : Long

    @Update
    fun update(movie: Movie)

    @Delete
    fun delete(movie: Movie)

    @Query("DELETE FROM movie_table")
    fun deleteAll()

    @Query("SELECT * FROM movie_table WHERE id = :movieId LIMIT 1")
    fun findMovie(movieId: Long) : List<Movie>

    @Query("SELECT * FROM movie_table ORDER BY movie_name ASC")
    fun getAllMovies() : LiveData<List<Movie>>

    @Query("SELECT * FROM movie_table WHERE wish_list = 1 ORDER BY movie_name ASC")
    fun getWishMovies() : LiveData<List<Movie>>

    @Query("SELECT * FROM movie_table WHERE watching = 1 ORDER BY movie_name ASC")
    fun getWatchingMovies() : LiveData<List<Movie>>

    @Query("SELECT * FROM movie_table WHERE watched = 1 ORDER BY movie_name ASC")
    fun getWatchedMovies() : LiveData<List<Movie>>
}