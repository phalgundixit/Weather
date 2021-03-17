package com.example.weather.viewModel

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.example.weather.model.City
import com.example.weather.repository.WeatherRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class WeatherViewModel @ViewModelInject constructor(private val weatherRepository: WeatherRepository): ViewModel() {
     val weatherResponse:MutableLiveData<City> = MutableLiveData()
    private val searchChannel = ConflatedBroadcastChannel<String>()

    fun setSearchQuery(search:String)
    {
        searchChannel.offer(search)
    }

    fun getCityData()
    {
        viewModelScope.launch {
            searchChannel.asFlow()
                .flatMapLatest { search->
                    weatherRepository.getCityData(search)
                }.catch {e->
                    Log.d("main", "${e.message}")
                }.collect { response->
                    weatherResponse.value=response
                }
        }
    }

    val getWeatherData:LiveData<List<City>> = weatherRepository.getWeatherData
        .flowOn(Dispatchers.Main).asLiveData(context = viewModelScope.coroutineContext)
    fun insertWeatherData(weatherEntity: City?) =viewModelScope.launch {
        weatherRepository.insertWeatherData(weatherEntity)
    }

}