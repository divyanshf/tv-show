package com.example.movie_tv

import android.content.Context
import android.provider.ContactsContract
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.movie_tv.login.UserDao
import com.example.movie_tv.login.UserModel
import com.example.movie_tv.movie.MovieDao
import com.example.movie_tv.movie.MovieModel

@Database(entities = [UserModel::class, MovieModel::class, RelationModel::class], version = 1)
abstract class RelationalDatabase : RoomDatabase() {
    abstract fun userDao():UserDao
    abstract fun movieDao(): MovieDao
    abstract fun relationDao(): RelationDao

    companion object{
        @Volatile private var instance:RelationalDatabase? = null

        @Synchronized fun getInstance(context: Context) : RelationalDatabase {
            if (instance == null){
                instance = Room.databaseBuilder(context, RelationalDatabase::class.java, "relational-db")
                    .build()
            }

            return instance as RelationalDatabase
        }
    }

}