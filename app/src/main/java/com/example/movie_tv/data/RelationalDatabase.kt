package com.example.movie_tv.data

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.movie_tv.data.dao.MovieDao
import com.example.movie_tv.data.model.Movie

@Database(entities = [Movie::class], version = 5)
abstract class RelationalDatabase : RoomDatabase() {
    abstract fun movieDao() : MovieDao

    companion object{
        private var INSTANCE: RelationalDatabase? = null
        private lateinit var sharedPreferences:SharedPreferences

        fun getDatabase(context: Context) : RelationalDatabase {
            sharedPreferences = context.getSharedPreferences("com.example.movie_tv.data", 0)
            if(INSTANCE == null){
                synchronized(RelationalDatabase::class){
                    if(INSTANCE == null){
                        INSTANCE = Room.databaseBuilder(context.applicationContext, RelationalDatabase::class.java, "relational_database")
                                .fallbackToDestructiveMigration()
                                .build()
                    }
                }
            }
            return INSTANCE!!
        }
    }
}