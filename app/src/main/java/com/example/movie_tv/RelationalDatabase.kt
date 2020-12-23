package com.example.movie_tv

import android.content.Context
import android.os.AsyncTask
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [User::class, Movie::class], version = 1)
abstract class RelationalDatabase : RoomDatabase() {
    abstract fun userDao() : UserDao
    abstract fun movieDao() : MovieDao

    companion object{
        private var INSTANCE:RelationalDatabase? = null

        fun getDatabase(context: Context) : RelationalDatabase {
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

        private var callback:RoomDatabase.Callback = object:RoomDatabase.Callback(){
            override fun onOpen(db: SupportSQLiteDatabase) {
                super.onOpen(db)
                PopulateDbAsync(INSTANCE!!).execute()
            }
        }

        private class PopulateDbAsync(db:RelationalDatabase) : AsyncTask<Void, Void, Void>(){
            private var mDao:MovieDao = db.movieDao()
            override fun doInBackground(vararg params: Void?): Void? {
                mDao.deleteAll()
                for (i in 0..5) {
                    var movie:Movie = Movie(i.toString(), "Doctor Strange $i", "https://picsum.photos/200", i*2, -1)
                    mDao.insert(movie)
                }
                return null
            }
        }
    }
}