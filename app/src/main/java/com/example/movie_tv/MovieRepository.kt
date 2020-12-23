package com.example.movie_tv

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData

class MovieRepository (application: Application) {
    private var db:RelationalDatabase = RelationalDatabase.getDatabase(application)
    private var mMovieDao:MovieDao = db.movieDao()
    private var mAllMovies: LiveData<List<Movie>> = mMovieDao.getAllMovies()

    fun getAllMovies() : LiveData<List<Movie>>{
        return mAllMovies
    }

    fun insert(movie:Movie){
        InsertAsyncTask(mMovieDao).execute(movie)
    }

    fun update(movie:Movie){
        UpdateAsyncTask(mMovieDao).execute(movie)
    }

    fun delete(movie: Movie){
        DeleteAsyncTask(mMovieDao).execute(movie)
    }

    fun deleteAll(){

    }

    companion object{
        private class InsertAsyncTask(dao:MovieDao) : AsyncTask<Movie, Void, Void>(){
            private var tmpDao:MovieDao = dao
            override fun doInBackground(vararg params: Movie?): Void? {
                tmpDao.insert(params[0]!!)
                return null
            }
        }
        private class UpdateAsyncTask(dao:MovieDao) : AsyncTask<Movie, Void, Void>(){
            private var tmpDao:MovieDao = dao
            override fun doInBackground(vararg params: Movie?): Void? {
                tmpDao.update(params[0]!!)
                return null
            }
        }
        private class DeleteAsyncTask(dao:MovieDao) : AsyncTask<Movie, Void, Void>(){
            private var tmpDao:MovieDao = dao
            override fun doInBackground(vararg params: Movie?): Void? {
                tmpDao.delete(params[0]!!)
                return null
            }
        }
        private class DeleteAllAsyncTask(dao:MovieDao) : AsyncTask<Void, Void, Void>(){
            private var tmpDao:MovieDao = dao
            override fun doInBackground(vararg params: Void?): Void? {
                tmpDao.deleteAll()
                return null
            }
        }
    }
}