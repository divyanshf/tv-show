package com.example.movie_tv.data.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.movie_tv.data.MovieRepository
import com.example.movie_tv.data.model.Movie

class MovieViewModel(application: Application) : AndroidViewModel(application) {
    private var mMovieRepository: MovieRepository = MovieRepository(application)
    private var mAllMovies:LiveData<List<Movie>> = mMovieRepository.getAllMovies()
    private var mWishMovies:LiveData<List<Movie>> = mMovieRepository.getWishMovies()
    private var mWatchedMovies:LiveData<List<Movie>> = mMovieRepository.getWatchedMovies()
    private var mWatchingMovies:LiveData<List<Movie>> = mMovieRepository.getWatchingMovies()

    fun insert(movie: Movie){
        mMovieRepository.insert(movie)
    }

    fun update(movie: Movie){
        mMovieRepository.update(movie)
    }

    fun delete(movie: Movie){
        mMovieRepository.delete(movie)
    }

    fun deleteAll(){
        mMovieRepository.deleteAll()
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