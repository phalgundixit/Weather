package com.example.weather.model

data class City(
    val weather:ArrayList<Weather>,
    val main: Main,
    val wind: Wind,
    val name:String
) {
}

