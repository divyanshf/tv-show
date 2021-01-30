package com.example.movie_tv.data

import com.example.movie_tv.data.dao.MovieDao
import com.example.movie_tv.data.model.Movie
import com.example.movie_tv.data.source.RemoteMovieDataSource
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class MovieRepository
constructor(
    private val mMovieDao:MovieDao,
    private var mRemoteMovieDataSource: RemoteMovieDataSource
) {
    private var mAllMovies = mMovieDao.getAllMovies()
    private var mWishMovies = mMovieDao.getWishMovies()
    private var mWatchingMovies = mMovieDao.getWatchingMovies()
    private var mWatchedMovies = mMovieDao.getWatchedMovies()

    suspend fun findMovie(movieId: Long) : Boolean{
         val found:List<Movie> = mMovieDao.findMovie(movieId)
         if (found.size == 1)
             return true
         return false
    }

    fun getAllMovies() : Flow<List<Movie>>{
        return mAllMovies
    }

    fun getWishMovies() : Flow<List<Movie>>{
        return mWishMovies
    }

    fun getWatchedMovies() : Flow<List<Movie>>{
        return mWatchedMovies
    }

    fun getWatchingMovies() : Flow<List<Movie>>{
        return mWatchingMovies
    }

    suspend fun insert(movie: Movie){
        withContext(IO){
            val newId = mMovieDao.insert(movie)
            mRemoteMovieDataSource.insert(getMovieMap(movie, newId))
        }
    }

    suspend fun update(movie: Movie){
        withContext(IO) {
            mMovieDao.update(movie)
            mRemoteMovieDataSource.update(getMovieMap(movie, movie.movieId))
        }
    }

    suspend fun delete(movie: Movie){
        withContext(IO) {
            mMovieDao.delete(movie)
            mRemoteMovieDataSource.delete(getMovieMap(movie, movie.movieId))
        }
    }

    suspend fun deleteAll(){
        withContext(IO) {
            mMovieDao.deleteAll()
        }
    }

    fun syncMovies(){
        mRemoteMovieDataSource.syncMovies()
    }

    private fun getMovieMap(movie : Movie, id : Long) : HashMap<String, Any>{
        val movieMap = HashMap<String, Any>()
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
}