package com.example.movie_tv

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Junction
import androidx.room.Relation
import com.example.movie_tv.login.UserModel
import com.example.movie_tv.movie.MovieModel

@Entity(primaryKeys = ["username", "movieId"])
data class RelationModel(
    val username:String,
    val movieId:String,
    val option:Int
)

data class UserWithMovies(
    @Embedded val user: UserModel,
    @Relation(
        parentColumn = "username",
        entityColumn = "movieId",
        associateBy = Junction(RelationModel::class)
    )
    val movies: List<MovieModel>
)
