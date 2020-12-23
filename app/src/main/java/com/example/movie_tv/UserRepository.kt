package com.example.movie_tv

import android.app.Application
import android.os.AsyncTask

class UserRepository (application: Application) {
    private var db:RelationalDatabase = RelationalDatabase.getDatabase(application)
    private var mUserDao:UserDao = db.userDao()
//    private var mAllWords:LiveData<List<User>> = mUserDao.getAllWords()
    private var mUser:User = mUserDao.getUser();

//    fun getAllWords() : LiveData<List<User>>{
//        return mAllWords
//    }

    fun getUser():User{
        return mUser
    }

    fun insert(user:User){
        InsertAsyncTask(mUserDao).execute(user)
    }

    fun delete(user:User){
        DeleteAsyncTask(mUserDao).execute(user)
    }

    companion object{
        private class InsertAsyncTask(dao:UserDao) : AsyncTask<User, Void, Void>(){
            private var tmpDao:UserDao = dao
            override fun doInBackground(vararg params: User?): Void? {
                tmpDao.insert(params[0]!!)
                return null
            }
        }
        private class DeleteAsyncTask(dao:UserDao) : AsyncTask<User, Void, Void>(){
            private var tmpDao:UserDao = dao
            override fun doInBackground(vararg params: User?): Void? {
                tmpDao.delete(params[0]!!)
                return null
            }
        }
    }
}