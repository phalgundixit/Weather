package com.example.weather.viewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.weather.MainCoroutineRule
import com.example.weather.model.City
import com.example.weather.model.Main
import com.example.weather.model.Weather
import com.example.weather.model.Wind
import com.example.weather.other.Status
import com.example.weather.repository.WeatherRepository
import com.google.common.truth.ExpectFailure.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock

class WeatherViewModelTest {


    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel: WeatherViewModel

    @Mock
    private lateinit var repository: WeatherRepository

    @Before
    fun setup() {
        viewModel = WeatherViewModel(repository)
    }

    @Test
    fun `insert weather data with empty field, returns error`() {
        val weatherData = City(1234, arrayListOf(Weather("","")),
            Main(303.0,30), Wind(10F,20),"bangalore")


        val value = viewModel.insertWeatherData(weatherData)

        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `insert weather data with big double field instead of double, returns error`() {
        val weatherData = City(1234, arrayListOf(Weather("","")),
            Main(303.000000000000000000000000000000000000000000000,30), Wind(10F,20),"bangalore")


        val value = viewModel.insertWeatherData(weatherData)

        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)
    }



}