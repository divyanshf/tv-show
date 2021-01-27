package com.example.movie_tv.data

import com.example.movie_tv.data.model.ApiResult
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiTMDB {
    @GET("search/movie?language=en-US&page=1&include_adult=false")
    fun getResultFromApi(@Query("api_key") api_key:String, @Query("query") query:String) : Call<ApiResult>
}