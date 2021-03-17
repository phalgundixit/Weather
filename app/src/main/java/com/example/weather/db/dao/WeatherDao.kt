package com.example.weather.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.weather.model.City
import kotlinx.coroutines.flow.Flow
@Dao
interface WeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeatherData(weather: City?)

    @Query("select * from weather")
    fun getWeatherData(): Flow<List<City>>
}