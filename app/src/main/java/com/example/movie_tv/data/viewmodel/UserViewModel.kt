package com.example.movie_tv.data.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.movie_tv.data.UserRepository
import com.example.movie_tv.data.model.User

class UserViewModel(application: Application) : AndroidViewModel(application) {
    private var mUserRepository: UserRepository = UserRepository(application)
    private var mUser: User = mUserRepository.getUser()

    fun insert(user: User){
        mUserRepository.insert(user)
    }

    fun update(user: User){
        mUserRepository.update(user)
    }

    fun delete(user: User){
        mUserRepository.delete(user)
    }

    fun deleteAll(){
        mUserRepository.deleteAll()
    }

    fun getUser(): User {
        return mUser
    }
}