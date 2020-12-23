package com.example.movie_tv

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

@Entity(tableName = "user_table")
data class User(
        @PrimaryKey @NotNull val username:String,
        @NotNull val password:String
)
