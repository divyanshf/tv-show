package com.example.movie_tv.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull
import java.time.Year

@Entity(tableName = "movie_table")
data class Movie(
        @PrimaryKey(autoGenerate = true) @NotNull @ColumnInfo(name = "id") val movieId:Int,
        @NotNull @ColumnInfo(name = "movie_name") val movieName:String,
        @NotNull @ColumnInfo(name = "movie_year") val movieYear:Int,
        @NotNull @ColumnInfo(name = "url") val movieURL:String,
        @NotNull @ColumnInfo(name = "rating") val movieRating:Int,
        @NotNull @ColumnInfo(name = "wish_list") var wishList:Boolean,
        @NotNull @ColumnInfo(name = "watching") var watching:Boolean,
        @NotNull @ColumnInfo(name = "watched") var watched:Boolean
){
    constructor(movieName: String, movieYear: Int, movieURL: String, movieRating: Int, wishList: Boolean, watching: Boolean, watched: Boolean) : this(0, movieName, movieYear, movieURL, movieRating, wishList, watching, watched)
}