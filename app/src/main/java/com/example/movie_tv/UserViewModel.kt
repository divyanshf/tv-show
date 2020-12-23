package com.example.movie_tv

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData

class UserViewModel(application: Application) : AndroidViewModel(application) {
    private var mUserRepository:UserRepository = UserRepository(application)
    private var mUser:User = mUserRepository.getUser()

    fun insert(user:User){
        mUserRepository.insert(user)
    }

    fun delete(user:User){
        mUserRepository.delete(user)
    }

    fun getUser():User{
        return mUser
    }
}