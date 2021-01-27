package com.example.movie_tv.data.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.movie_tv.data.MovieRepository
import com.example.movie_tv.data.model.Movie
import com.example.movie_tv.data.model.MovieJson
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class MovieViewModel(application: Application) : AndroidViewModel(application) {
    private var mMovieRepository: MovieRepository = MovieRepository(application)
    private var mAllMovies:LiveData<List<Movie>> = mMovieRepository.getAllMovies()
    private var mWishMovies:LiveData<List<Movie>> = mMovieRepository.getWishMovies()
    private var mWatchedMovies:LiveData<List<Movie>> = mMovieRepository.getWatchedMovies()
    private var mWatchingMovies:LiveData<List<Movie>> = mMovieRepository.getWatchingMovies()

    fun insert(movie: Movie){
        viewModelScope.launch {
            mMovieRepository.insert(movie)
        }
    }

    fun update(movie: Movie){
        viewModelScope.launch {
            mMovieRepository.update(movie)
        }
    }

    fun findMovies(movieId: Long) : Boolean{
        var result: Boolean

        runBlocking {
            result = mMovieRepository.findMovie(movieId)
        }

        return result
    }

    fun delete(movie: Movie){
        viewModelScope.launch {
            mMovieRepository.delete(movie)
        }
    }

    fun deleteAll(){
        viewModelScope.launch {
            mMovieRepository.deleteAll()
        }
    }

    fun syncMovies(){
        viewModelScope.launch {
            mMovieRepository.syncMovies()
        }
    }

    fun getAllMovies():LiveData<List<Movie>> {
        return mAllMovies
    }

    fun getWishMovies():LiveData<List<Movie>> {
        return mWishMovies
    }

    fun getWatchingMovies():LiveData<List<Movie>> {
        return mWatchingMovies
    }

    fun getWatchedMovies():LiveData<List<Movie>> {
        return mWatchedMovies
    }
}