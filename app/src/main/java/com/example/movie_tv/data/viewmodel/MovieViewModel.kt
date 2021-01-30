package com.example.movie_tv.data.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.movie_tv.data.MovieRepository
import com.example.movie_tv.data.model.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class MovieViewModel
@Inject
constructor(
    private val mMovieRepository: MovieRepository
) : ViewModel() {

    private var mAllMovies:LiveData<List<Movie>> = mMovieRepository.getAllMovies().asLiveData()
    private var mWishMovies:LiveData<List<Movie>> = mMovieRepository.getWishMovies().asLiveData()
    private var mWatchedMovies:LiveData<List<Movie>> = mMovieRepository.getWatchedMovies().asLiveData()
    private var mWatchingMovies:LiveData<List<Movie>> = mMovieRepository.getWatchingMovies().asLiveData()

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