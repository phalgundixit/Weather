package com.example.weather.network


import com.example.weather.model.City
import javax.inject.Inject

class ApiServiceImp @Inject constructor(private val apiService: ApiService) {

    suspend fun getCity(city:String,appId:String): City = apiService.getCityData(city,appId)
}