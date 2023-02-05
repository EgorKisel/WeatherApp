package com.example.weatherapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherapp.repository.RepositoryImpl

class MainViewModel(
    private val liveData: MutableLiveData<AppState> = MutableLiveData(),
    private val repository: RepositoryImpl = RepositoryImpl()
) :
    ViewModel() {

    fun getData(): LiveData<AppState> {
        return liveData
    }

    fun getWeatherRussia() = getWeather(true)
    fun getWeatherWorld() = getWeather(false)

    private fun getWeather(isRussian: Boolean) {
        Thread {
            liveData.postValue(AppState.Loading)

            if (true) {
                val answer =
                    if (isRussian) repository.getRussianWeatherFromLocalStorage()
                    else repository.getWorldWeatherFromLocalStorage()
                liveData.postValue(AppState.Success(answer))
            } else {
                liveData.postValue(AppState.Error(IllegalAccessError("AAAAAAAAAAAAAAAAA!!!!!")))
            }

        }.start()
    }
}