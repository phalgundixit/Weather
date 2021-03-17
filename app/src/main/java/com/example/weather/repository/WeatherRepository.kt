package com.example.weather.repository


import com.example.weather.model.City
import com.example.weather.network.ApiServiceImp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class WeatherRepository @Inject constructor(private val apiServiceImp: ApiServiceImp) {

    fun getCityData(city:String):Flow<City> = flow {
        val response= apiServiceImp.getCity(city,"3e968f0ca9485106c69697281e0d75d9")
        emit(response)
    }.flowOn(Dispatchers.IO)
        .conflate()
}