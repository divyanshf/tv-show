package com.example.movie_tv

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface MovieDao {

    @Insert
    fun insert(movie: Movie)

    @Update
    fun update(movie: Movie)

    @Delete
    fun delete(movie: Movie)

    @Query("DELETE FROM movie_table")
    fun deleteAll()

    @Query("SELECT * FROM movie_table ORDER BY movie_name ASC")
    fun getAllMovies() : LiveData<List<Movie>>
}