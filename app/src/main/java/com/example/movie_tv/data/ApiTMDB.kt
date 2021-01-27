package com.example.movie_tv.data

import com.example.movie_tv.data.model.ApiResult
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiTMDB {
    @GET("discover/movie?language=en-US&sort_by=popularity.desc&include_adult=false&include_video=true&page=1")
    fun getResultFromApi(@Query("api_key") api_key:String) : Call<ApiResult>
}