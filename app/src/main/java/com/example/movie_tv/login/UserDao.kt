package com.example.movie_tv.login

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface UserDao {
    @Insert
    fun insert(user:UserModel)

//    @Update
//    fun update(user:UserModel)
//
//    @Delete
//    fun delete(user:UserModel)

    @Query("SELECT * FROM user_table WHERE user_table.username = username LIMIT 1")
    fun getUser(username:String)
}