package com.example.movie_tv.data

import android.app.Application
import android.os.AsyncTask
import com.example.movie_tv.MainActivity
import com.example.movie_tv.data.dao.UserDao
import com.example.movie_tv.data.model.User

class UserRepository (application: Application) {
    private var db: RelationalDatabase = RelationalDatabase.getDatabase(application)
    private var mUserDao: UserDao = db.userDao()
//    private var mUser: User = mUserDao.getUser();


    fun getUser(): User? {
        return GetUserAsyncTask(mUserDao).execute().get()
    }

    fun insert(user: User){
        InsertAsyncTask(mUserDao).execute(user)
    }

    fun update(user: User){
        UpdateAsyncTask(mUserDao).execute(user)
    }

    fun delete(user: User){
        DeleteAsyncTask(mUserDao).execute(user)
    }

    fun deleteAll(){
        DeleteAllAsyncTask(mUserDao).execute()
    }

    companion object{
        private class InsertAsyncTask(dao: UserDao) : AsyncTask<User, Void, Void>(){
            private var tmpDao: UserDao = dao
            override fun doInBackground(vararg params: User?): Void? {
                tmpDao.insert(params[0]!!)
                return null
            }
        }
        private class UpdateAsyncTask(dao: UserDao) : AsyncTask<User, Void, Void>(){
            private var tmpDao: UserDao = dao
            override fun doInBackground(vararg params: User?): Void? {
                tmpDao.update(params[0]!!)
                return null
            }
        }
        private class DeleteAsyncTask(dao: UserDao) : AsyncTask<User, Void, Void>(){
            private var tmpDao: UserDao = dao
            override fun doInBackground(vararg params: User?): Void? {
                tmpDao.delete(params[0]!!)
                return null
            }
        }
        private class DeleteAllAsyncTask(dao: UserDao) : AsyncTask<Void, Void, Void>(){
            private var tmpDao: UserDao = dao
            override fun doInBackground(vararg params: Void?): Void? {
                tmpDao.deleteAll()
                return null
            }
        }
        private class GetUserAsyncTask(dao: UserDao) : AsyncTask<Void, Void, User>(){
            private var tmpDao: UserDao = dao
            override fun doInBackground(vararg params: Void?): User {
                return tmpDao.getUser()
            }
        }
    }
}