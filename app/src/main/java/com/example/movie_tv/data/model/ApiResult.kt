package com.example.movie_tv.data.model

data class ApiResult(
    val page:Int,
    val results:List<MovieJson>,
    val total_results:Int,
    val total_pages:Int
)
