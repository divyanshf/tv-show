package com.example.movie_tv.data

import android.app.Application
import android.os.AsyncTask
import com.example.movie_tv.data.dao.UserDao
import com.example.movie_tv.data.model.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserRepository (application: Application) {
    private var db: RelationalDatabase = RelationalDatabase.getDatabase(application)
    private var mUserDao: UserDao = db.userDao()
//    private var mUser: User = mUserDao.getUser();


    fun getUser(): User? {
        var user:User? = null
        CoroutineScope(Dispatchers.IO).launch {
            user = getSuspendUser()
        }
        return user
//        return GetUserAsyncTask(mUserDao).execute().get()
    }

    private suspend fun getSuspendUser():User?{
        return mUserDao.getUser()
    }

    fun insert(user: User){
        CoroutineScope(Dispatchers.IO).launch {
            mUserDao.insert(user)
        }
//        InsertAsyncTask(mUserDao).execute(user)
    }

    fun update(user: User){
        CoroutineScope(Dispatchers.IO).launch {
            mUserDao.update(user)
        }
//        UpdateAsyncTask(mUserDao).execute(user)
    }

    fun delete(user: User){
        CoroutineScope(Dispatchers.IO).launch {
            mUserDao.delete(user)
        }
//        DeleteAsyncTask(mUserDao).execute(user)
    }

    fun deleteAll(){
        CoroutineScope(Dispatchers.IO).launch {
            mUserDao.deleteAll()
        }
//        DeleteAllAsyncTask(mUserDao).execute()
    }

//    companion object{
//        private class InsertAsyncTask(dao: UserDao) : AsyncTask<User, Void, Void>(){
//            private var tmpDao: UserDao = dao
//            override fun doInBackground(vararg params: User?): Void? {
//                tmpDao.insert(params[0]!!)
//                return null
//            }
//        }
//        private class UpdateAsyncTask(dao: UserDao) : AsyncTask<User, Void, Void>(){
//            private var tmpDao: UserDao = dao
//            override fun doInBackground(vararg params: User?): Void? {
//                tmpDao.update(params[0]!!)
//                return null
//            }
//        }
//        private class DeleteAsyncTask(dao: UserDao) : AsyncTask<User, Void, Void>(){
//            private var tmpDao: UserDao = dao
//            override fun doInBackground(vararg params: User?): Void? {
//                tmpDao.delete(params[0]!!)
//                return null
//            }
//        }
//        private class DeleteAllAsyncTask(dao: UserDao) : AsyncTask<Void, Void, Void>(){
//            private var tmpDao: UserDao = dao
//            override fun doInBackground(vararg params: Void?): Void? {
//                tmpDao.deleteAll()
//                return null
//            }
//        }
//        private class GetUserAsyncTask(dao: UserDao) : AsyncTask<Void, Void, User>(){
//            private var tmpDao: UserDao = dao
//            override fun doInBackground(vararg params: Void?): User {
//                return tmpDao.getUser()
//            }
//        }
//    }
}