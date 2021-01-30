package com.example.movie_tv.dependency_injection

import com.example.movie_tv.data.dao.MovieDao
import com.example.movie_tv.data.source.RemoteMovieDataSource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DataSourceModule {

    @Singleton
    @Provides
    fun provideRemoteMovieDataSource(movieDao: MovieDao, firebaseFirestore: FirebaseFirestore, firebaseAuth: FirebaseAuth) : RemoteMovieDataSource{
        return RemoteMovieDataSource(movieDao, firebaseFirestore, firebaseAuth)
    }

}