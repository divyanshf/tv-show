package com.example.movie_tv.login

import android.app.Application
import com.example.movie_tv.RelationalDatabase

class UserRepository(application:Application) {
    var database:RelationalDatabase = RelationalDatabase.getInstance(application)

    private var userDao:UserDao = database.userDao()

    fun insert(user:UserModel){
        //  Insert a user
    }
}