package com.example.weather.db.entity

import androidx.room.TypeConverter
import com.example.weather.model.Main
import com.example.weather.model.Weather
import com.example.weather.model.Wind
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {

    @TypeConverter
    fun fromString(value: String?): ArrayList<Weather> {
        val listType =object : TypeToken<ArrayList<Weather>>(){}.type
        return Gson().fromJson(value, listType)
    }
    @TypeConverter
    fun frmArrayList(list: ArrayList<Weather?>): String {
        return Gson().toJson(list)
    }

    @TypeConverter
    fun mainToJson(value: Main) = Gson().toJson(value)

    @TypeConverter
    fun jsonToMain(value: String) = Gson().fromJson(value, Main::class.java)

    @TypeConverter
    fun windToJson(value: Wind) = Gson().toJson(value)

    @TypeConverter
    fun jsonToWind(value: String) = Gson().fromJson(value, Wind::class.java)

}