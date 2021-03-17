package com.example.weather.db.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.asLiveData
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.weather.db.database.WeatherDatabase
import com.example.weather.model.City
import com.example.weather.model.Main
import com.example.weather.model.Weather
import com.example.weather.model.Wind
import com.google.common.truth.Subject
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runBlockingTest
import org.junit.*
import org.junit.runner.RunWith
import javax.inject.Inject
import javax.inject.Named

@ExperimentalCoroutinesApi
@SmallTest
@HiltAndroidTest
class WeatherDaoTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    @Named("test_db")
    lateinit var database: WeatherDatabase
    private lateinit var dao: WeatherDao

    @Before
    fun setup() {
        hiltRule.inject()
        dao = database.weatherDao()
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun insertWeatherData()= runBlockingTest {

        val weatherData = City(1234, arrayListOf(Weather("haze","50d")),Main(303.0,30), Wind(10F,20),"bangalore")
        dao.insertWeatherData(weatherData)

        val getData = dao.getWeatherData()
        assertThat(getData.toList()).contains(weatherData)
    }


}


