package com.example.movie_tv.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.movie_tv.data.model.Movie

@Dao
interface MovieDao {

    @Insert
    suspend fun insert(movie: Movie)

    @Update
    suspend fun update(movie: Movie)

    @Delete
    suspend fun delete(movie: Movie)

    @Query("DELETE FROM movie_table")
    suspend fun deleteAll()

    @Query("SELECT * FROM movie_table ORDER BY movie_name ASC")
    fun getAllMovies() : LiveData<List<Movie>>
}