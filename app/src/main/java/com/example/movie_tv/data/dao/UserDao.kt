package com.example.movie_tv.data.dao

import androidx.room.*
import com.example.movie_tv.data.model.User

@Dao
interface UserDao {

    @Insert
    fun insert(user: User)

    @Update
    fun update(user: User)

    @Delete
    fun delete(user: User)

    @Query("DELETE FROM user_table")
    fun deleteAll()

    @Query("SELECT * FROM user_table LIMIT 1")
    fun getUser() : User

//    @Query("DELETE FROM user_table")
//    fun deleteAll()

//    @Query("SELECT * FROM user_table ORDER BY word ASC")
//    fun getAllWords() : LiveData<List<User>>
}