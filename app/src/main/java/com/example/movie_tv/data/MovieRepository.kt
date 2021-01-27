package com.example.movie_tv.data

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import com.example.movie_tv.data.local.dao.MovieDao
import com.example.movie_tv.data.model.Movie
import com.example.movie_tv.data.remote.MovieFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

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

    fun findMovie(movieId: Long) : Boolean{
        var found:List<Movie>
        runBlocking {
            found = mMovieDao.findMovie(movieId)
        }
        if (found.size == 1)
            return true
        return false
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

    suspend fun insert(movie: Movie){
        withContext(IO){
            val newId = mMovieDao.insert(movie)
            Log.i("NEWID", newId.toString())
            mMovieFirestore.insert(getMovieMap(movie, newId))
        }
    }

    suspend fun update(movie: Movie){
        withContext(IO) {
            mMovieDao.update(movie)
            mMovieFirestore.update(getMovieMap(movie, movie.movieId))
        }
    }

    suspend fun delete(movie: Movie){
        withContext(IO) {
            mMovieDao.delete(movie)
            mMovieFirestore.delete(getMovieMap(movie, movie.movieId))
        }
    }

    suspend fun deleteAll(){
        withContext(IO) {
            mMovieDao.deleteAll()
        }
    }

    suspend fun syncMovies(){
        withContext(IO) {
            mMovieFirestore.syncMovies()
        }
    }
}