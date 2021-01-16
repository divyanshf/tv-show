package com.example.movie_tv.data

import android.content.Context
import android.content.SharedPreferences
import android.os.AsyncTask
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.movie_tv.data.dao.MovieDao
import com.example.movie_tv.data.dao.UserDao
import com.example.movie_tv.data.model.Movie
import com.example.movie_tv.data.model.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

@Database(entities = [User::class, Movie::class], version = 4)
abstract class RelationalDatabase : RoomDatabase() {
    abstract fun userDao() : UserDao
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
                                .addCallback(callback)
                                .build()
                    }
                }
            }
            return INSTANCE!!
        }

        private var callback: Callback = object: Callback(){
            override fun onOpen(db: SupportSQLiteDatabase) {
                super.onOpen(db)
                val isPopulated = sharedPreferences.getBoolean("POPULATED", false)
                if(!isPopulated){
                    CoroutineScope(IO).launch {
                        populateDB(INSTANCE!!)
                    }
                }
            }
        }

        private suspend fun populateDB(db: RelationalDatabase){
            val uDao = db.userDao()
            val mDao = db.movieDao()

            //  User
            Log.i("POPULATE", "POPULATING")
            uDao.deleteAll()
            uDao.insert(User("test", "password", false))

            //  Movies
            mDao.deleteAll()
            for (i in 0..5) {
                val movie = Movie("Doctor Strange $i", i + 2015, "https://picsum.photos/200", i , wishList = true, watching = false, watched = false)
                if(i % 2 == 0){
                    movie.watching = true
                }else{
                    movie.watched = true
                }
                mDao.insert(movie)
            }
            sharedPreferences.edit().putBoolean("POPULATED", true).apply()
        }
    }
}