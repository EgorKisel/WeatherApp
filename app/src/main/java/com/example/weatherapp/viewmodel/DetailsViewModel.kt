package com.example.weatherapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherapp.repository.*
import com.example.weatherapp.repository.dto.WeatherDTO

class DetailsViewModel(
    private val liveData: MutableLiveData<DetailsState> = MutableLiveData(),
    private val repository: DetailsRepository = DetailsRepositoryRetrofit2Impl()
) : ViewModel() {

    fun getLivedata() = liveData

    fun getWeather(city: City) {
        liveData.postValue(DetailsState.Loading)
        repository.getWeatherDetails(city, object : Callback {

            override fun onResponse(weatherDTO: WeatherDTO) {
                liveData.postValue(DetailsState.Success(weatherDTO))
            }

            override fun onFail() {
                //liveData.postValue(DetailsState.Error())
            }

        })
    }

    interface Callback {
        fun onResponse(weatherDTO: WeatherDTO)
        fun onFail()
    }
}