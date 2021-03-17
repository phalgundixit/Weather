package com.example.weather.module

import android.content.Context
import androidx.room.Room
import com.example.weather.db.dao.WeatherDao
import com.example.weather.db.database.WeatherDatabase
import com.example.weather.network.ApiService
import com.example.weather.network.ApiServiceImp
import com.example.weather.repository.WeatherRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object TestAppModule {

    @Provides
    @Named("test_db")
    fun provideInMemoryDb(@ApplicationContext context: Context) =
        Room.inMemoryDatabaseBuilder(context, WeatherDatabase::class.java)
            .allowMainThreadQueries()
            .build()
    @Provides
    fun providesWeatherDao(weatherDatabase: WeatherDatabase): WeatherDao = weatherDatabase.weatherDao()

    @Provides
    fun providesWeatherRepository(weatherDao: WeatherDao, apiServiceImp: ApiServiceImp): WeatherRepository =
        WeatherRepository(apiServiceImp,weatherDao)

    @Provides
    @Singleton
    fun getBaseUrl():String = "https://api.openweathermap.org/data/2.5/"

    @Provides
    @Singleton
    fun getRetrofitBuilder(baseUrl:String): Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun getApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)
}