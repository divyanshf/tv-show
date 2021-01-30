package com.example.movie_tv.dependency_injection

import com.example.movie_tv.data.MovieRepository
import com.example.movie_tv.data.dao.MovieDao
import com.example.movie_tv.data.source.RemoteMovieDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {

    @Singleton
    @Provides
    fun provideMovieRepository(movieDao: MovieDao, remoteMovieDataSource: RemoteMovieDataSource) : MovieRepository {
        return MovieRepository(movieDao, remoteMovieDataSource)
    }

}