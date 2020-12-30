package com.example.movie_tv.data.dao

import androidx.room.*
import com.example.movie_tv.data.model.User

@Dao
interface UserDao {

    @Insert
    suspend fun insert(user: User)

    @Update
    suspend fun update(user: User)

    @Delete
    suspend fun delete(user: User)

    @Query("DELETE FROM user_table")
    suspend fun deleteAll()

    @Query("SELECT * FROM user_table LIMIT 1")
    suspend fun getUser() : User
}