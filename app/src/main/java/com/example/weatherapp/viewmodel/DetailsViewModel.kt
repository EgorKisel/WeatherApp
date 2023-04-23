package com.example.weatherapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherapp.MyApp.Companion.getHistoryDao
import com.example.weatherapp.repository.*
import com.example.weatherapp.repository.dto.WeatherDTO

class DetailsViewModel(
    private val liveData: MutableLiveData<DetailsState> = MutableLiveData(),
    private val repository: DetailsRepositoryOne = DetailsRepositoryOneRetrofit2Impl(),
    private val historyRepo: LocalRepository = LocalRepositoryRoomImpl(getHistoryDao())
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

    fun saveCityToDB(weather: Weather) {
        Thread {
            historyRepo.saveEntity(weather)
        }.start()
    }

    interface Callback {
        fun onResponse(weatherDTO: WeatherDTO)
        fun onFail()
    }
}