package com.example.weather.network


import com.example.weather.model.City
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("weather/")
    suspend fun getCityData(
        @Query("q") q:String,
        @Query("appid") appId:String
    ): City
}