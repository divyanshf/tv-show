package com.example.movie_tv.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.movie_tv.data.dao.MovieDao
import com.example.movie_tv.data.model.Movie

@Database(entities = [Movie::class], exportSchema = false, version = 5)
abstract class RelationalDatabase : RoomDatabase() {
    abstract fun movieDao() : MovieDao

    companion object{
        const val DATABASE_NAME = "relational_database"
    }
}