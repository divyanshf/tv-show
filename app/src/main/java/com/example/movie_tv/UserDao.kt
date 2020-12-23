package com.example.movie_tv

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDao {

    @Insert
    fun insert(user: User)

    @Delete
    fun delete(user: User)

    @Query("SELECT * FROM user_table LIMIT 1")
    fun getUser() : User

//    @Query("DELETE FROM user_table")
//    fun deleteAll()

//    @Query("SELECT * FROM user_table ORDER BY word ASC")
//    fun getAllWords() : LiveData<List<User>>
}