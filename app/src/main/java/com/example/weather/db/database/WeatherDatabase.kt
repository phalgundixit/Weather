package com.example.weather.db.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.weather.db.dao.WeatherDao
import com.example.weather.db.entity.Converters
import com.example.weather.model.City

@Database(entities = [City::class],version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class WeatherDatabase:RoomDatabase() {
    abstract fun weatherDao(): WeatherDao
}