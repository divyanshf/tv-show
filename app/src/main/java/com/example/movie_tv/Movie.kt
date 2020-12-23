package com.example.movie_tv

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

@Entity(tableName = "movie_table")
data class Movie(
        @PrimaryKey @NotNull @ColumnInfo(name = "id") val movieId:String,
        @NotNull @ColumnInfo(name = "movie_name") val movieName:String,
        @NotNull @ColumnInfo(name = "url") val movieURL:String,
        @NotNull @ColumnInfo(name = "rating") val movieRating:Int,
        @NotNull @ColumnInfo(name = "option") val option:Int
)