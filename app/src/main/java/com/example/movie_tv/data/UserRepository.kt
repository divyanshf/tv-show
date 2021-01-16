package com.example.movie_tv.data

import android.app.Application
import android.os.AsyncTask
import com.example.movie_tv.data.dao.UserDao
import com.example.movie_tv.data.model.User
import kotlinx.coroutines.*

class UserRepository (application: Application) {
    private var db: RelationalDatabase = RelationalDatabase.getDatabase(application)
    private var mUserDao: UserDao = db.userDao()

    fun getUser(): User? {
        var user: User?
        runBlocking {
            user = getSuspendUser()
        }
        return user
    }

    private suspend fun getSuspendUser():User?{
        return mUserDao.getUser()
    }

    fun insert(user: User){
        CoroutineScope(Dispatchers.IO).launch {
            mUserDao.insert(user)
        }
    }

    fun update(user: User){
        CoroutineScope(Dispatchers.IO).launch {
            mUserDao.update(user)
        }
    }

    fun delete(user: User){
        CoroutineScope(Dispatchers.IO).launch {
            mUserDao.delete(user)
        }
    }

    fun deleteAll(){
        CoroutineScope(Dispatchers.IO).launch {
            mUserDao.deleteAll()
        }
    }
}