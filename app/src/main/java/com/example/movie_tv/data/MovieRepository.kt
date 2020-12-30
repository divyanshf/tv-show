package com.example.movie_tv.data

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import com.example.movie_tv.data.dao.MovieDao
import com.example.movie_tv.data.model.Movie
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class MovieRepository (application: Application) {
    private var db: RelationalDatabase = RelationalDatabase.getDatabase(application)
    private var mMovieDao: MovieDao = db.movieDao()
    private var mAllMovies: LiveData<List<Movie>> = mMovieDao.getAllMovies()

    fun getAllMovies() : LiveData<List<Movie>>{
        return mAllMovies
    }

    fun insert(movie: Movie){
        CoroutineScope(IO).launch {
            mMovieDao.insert(movie)
        }
//        InsertAsyncTask(mMovieDao).execute(movie)
    }

    fun update(movie: Movie){
        CoroutineScope(IO).launch {
            mMovieDao.update(movie)
        }
//        UpdateAsyncTask(mMovieDao).execute(movie)
    }

    fun delete(movie: Movie){
        CoroutineScope(IO).launch {
            mMovieDao.delete(movie)
        }
//        DeleteAsyncTask(mMovieDao).execute(movie)
    }

    fun deleteAll(){
        CoroutineScope(IO).launch {
            mMovieDao.deleteAll()
        }
//        DeleteAllAsyncTask(mMovieDao).execute()
    }

//    companion object{
//        private class InsertAsyncTask(dao: MovieDao) : AsyncTask<Movie, Void, Void>(){
//            private var tmpDao: MovieDao = dao
//            override fun doInBackground(vararg params: Movie?): Void? {
//                tmpDao.insert(params[0]!!)
//                return null
//            }
//        }
//        private class UpdateAsyncTask(dao: MovieDao) : AsyncTask<Movie, Void, Void>(){
//            private var tmpDao: MovieDao = dao
//            override fun doInBackground(vararg params: Movie?): Void? {
//                tmpDao.update(params[0]!!)
//                return null
//            }
//        }
//        private class DeleteAsyncTask(dao: MovieDao) : AsyncTask<Movie, Void, Void>(){
//            private var tmpDao: MovieDao = dao
//            override fun doInBackground(vararg params: Movie?): Void? {
//                tmpDao.delete(params[0]!!)
//                return null
//            }
//        }
//        private class DeleteAllAsyncTask(dao: MovieDao) : AsyncTask<Void, Void, Void>(){
//            private var tmpDao: MovieDao = dao
//            override fun doInBackground(vararg params: Void?): Void? {
//                tmpDao.deleteAll()
//                return null
//            }
//        }
//    }
}