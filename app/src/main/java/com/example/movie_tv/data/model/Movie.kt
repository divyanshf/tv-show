package com.example.movie_tv.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

@Entity(tableName = "movie_table")
data class Movie(
    @PrimaryKey(autoGenerate = true) @NotNull @ColumnInfo(name = "id") val movieId: Long,
    @NotNull @ColumnInfo(name = "movie_name") val movieName:String,
    @NotNull @ColumnInfo(name = "movie_year") val movieYear: Long,
    @NotNull @ColumnInfo(name = "url") val movieURL:String,
    @NotNull @ColumnInfo(name = "rating") val movieRating:Long,
    @NotNull @ColumnInfo(name = "wish_list") var wishList:Boolean,
    @NotNull @ColumnInfo(name = "watching") var watching:Boolean,
    @NotNull @ColumnInfo(name = "watched") var watched:Boolean
){
    constructor(movieName: String, movieYear: Long, movieURL: String, movieRating: Long, wishList: Boolean, watching: Boolean, watched: Boolean) : this(0, movieName, movieYear, movieURL, movieRating, wishList, watching, watched)
}