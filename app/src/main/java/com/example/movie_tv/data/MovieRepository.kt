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
    private var mWishMovies: LiveData<List<Movie>> = mMovieDao.getWishMovies()
    private var mWatchingMovies: LiveData<List<Movie>> = mMovieDao.getWatchingMovies()
    private var mWatchedMovies: LiveData<List<Movie>> = mMovieDao.getWatchedMovies()

    fun getAllMovies() : LiveData<List<Movie>>{
        return mAllMovies
    }

    fun getWishMovies() : LiveData<List<Movie>>{
        return mWishMovies
    }

    fun getWatchedMovies() : LiveData<List<Movie>>{
        return mWatchedMovies
    }

    fun getWatchingMovies() : LiveData<List<Movie>>{
        return mWatchingMovies
    }

    fun insert(movie: Movie){
        CoroutineScope(IO).launch {
            mMovieDao.insert(movie)
        }
    }

    fun update(movie: Movie){
        CoroutineScope(IO).launch {
            mMovieDao.update(movie)
        }
    }

    fun delete(movie: Movie){
        CoroutineScope(IO).launch {
            mMovieDao.delete(movie)
        }
    }

    fun deleteAll(){
        CoroutineScope(IO).launch {
            mMovieDao.deleteAll()
        }
    }
}