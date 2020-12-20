package com.example.movie_tv.login

import android.app.Application
import com.example.movie_tv.RelationalDatabase
import com.example.movie_tv.movie.MovieDao
import com.example.movie_tv.movie.MovieModel

class MovieRepository(application:Application) {
    var database:RelationalDatabase = RelationalDatabase.getInstance(application)

    private var movieDao: MovieDao = database.movieDao()

    fun insert(movie:MovieModel){
        //  Insert a movie
        movieDao.insert(movie)
    }

    fun update(movie:MovieModel){
        //  Update a movie
        movieDao.update(movie)
    }

    fun delete(movie:MovieModel){
        //  Delete a movie
        movieDao.delete(movie)
    }

    fun getMovie(movie:MovieModel){
        //  Get a movie
        movieDao.getMovie(movie.movieId)
    }
}