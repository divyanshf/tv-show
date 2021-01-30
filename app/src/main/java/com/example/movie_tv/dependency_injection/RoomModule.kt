package com.example.movie_tv.dependency_injection

import android.content.Context
import androidx.room.Room
import com.example.movie_tv.data.RelationalDatabase
import com.example.movie_tv.data.dao.MovieDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Singleton
    @Provides
    fun provideRelationalDatabase(@ApplicationContext context : Context) : RelationalDatabase{
        return Room.databaseBuilder(
            context,
            RelationalDatabase::class.java,
            RelationalDatabase.DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideMovieDao(db : RelationalDatabase) : MovieDao {
        return db.movieDao()
    }

}