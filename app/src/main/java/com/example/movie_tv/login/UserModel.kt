package com.example.movie_tv.login

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class UserModel(
    @PrimaryKey private val username:String,
    private val password:String
)
