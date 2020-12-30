package com.example.movie_tv.data

import android.content.Context
import android.content.SharedPreferences
import android.os.AsyncTask
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

@Database(entities = [User::class, Movie::class], version = 3)
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
                CoroutineScope(IO).launch {
                    populateDB(INSTANCE!!)
                }
//                PopulateDbAsync(INSTANCE!!).execute()
            }
        }

        private suspend fun populateDB(db: RelationalDatabase){
            val uDao = db.userDao()
            val mDao = db.movieDao()
            if(sharedPreferences.getBoolean("POPULATED", false)){
                //  User
                uDao.deleteAll()
                uDao.insert(User("test", "password", false))

                //  Movies
                mDao.deleteAll()
                for (i in 0..5) {
                    val movie = Movie("Doctor Strange $i", "https://picsum.photos/200", i , wishList = false, watching = false, watched = false)
                    mDao.insert(movie)
                }

                sharedPreferences.edit().putBoolean("POPULATED", true)
            }
        }

//        private class PopulateDbAsync(db: RelationalDatabase) : AsyncTask<Void, Void, Void>(){
//            private var mDao: MovieDao = db.movieDao()
//            private var uDao: UserDao = db.userDao()
//
//            override fun doInBackground(vararg params: Void?): Void? {
//                if(sharedPreferences.getBoolean("POPULATED", false)){
//                    //  User
//                    uDao.deleteAll()
//                    uDao.insert(User("test", "password", false))
//
//                    //  Movies
//                    mDao.deleteAll()
//                    for (i in 0..5) {
//                        val movie = Movie("Doctor Strange $i", "https://picsum.photos/200", i , wishList = false, watching = false, watched = false)
//                        mDao.insert(movie)
//                    }
//
//                    sharedPreferences.edit().putBoolean("POPULATED", true)
//                }
//                return null
//            }
//        }
    }
}