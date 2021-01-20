package com.example.movie_tv.data

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import com.example.movie_tv.data.dao.MovieDao
import com.example.movie_tv.data.model.Movie
import com.example.movie_tv.data.remote.MovieFirestore
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
    private var mMovieFirestore: MovieFirestore = MovieFirestore(mMovieDao)

    private fun getMovieMap(movie : Movie, id : Long) : HashMap<String, Any>{
        var movieMap = HashMap<String, Any>()
        movieMap["id"] = id
        movieMap["name"] = movie.movieName
        movieMap["year"] = movie.movieYear
        movieMap["url"] = movie.movieURL
        movieMap["rating"] = movie.movieRating
        movieMap["wishList"] = movie.wishList
        movieMap["watching"] = movie.watching
        movieMap["watched"] = movie.watched

        return movieMap
    }

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
            var newId = mMovieDao.insert(movie)
            Log.i("NEWID", newId.toString())
            mMovieFirestore.insert(getMovieMap(movie, newId))
        }
    }

    fun update(movie: Movie){
        CoroutineScope(IO).launch {
            mMovieDao.update(movie)
            mMovieFirestore.update(getMovieMap(movie, movie.movieId))
        }
    }

    fun delete(movie: Movie){
        CoroutineScope(IO).launch {
            mMovieDao.delete(movie)
            mMovieFirestore.delete(getMovieMap(movie, movie.movieId))
        }
    }

    fun deleteAll(){
        CoroutineScope(IO).launch {
            mMovieDao.deleteAll()
        }
    }

    fun syncMovies(){
        CoroutineScope(IO).launch {
            mMovieFirestore.syncMovies()
        }
    }
}