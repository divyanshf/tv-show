package com.example.movie_tv

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction

@Dao
interface RelationDao {
    @Transaction
    @Query("SELECT * FROM user_table")
    fun getUserWithMovies() : List<UserWithMovies>
}