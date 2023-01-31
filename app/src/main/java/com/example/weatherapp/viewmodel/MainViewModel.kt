package com.example.weatherapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherapp.repository.RepositoryImpl

class MainViewModel(private val liveData: MutableLiveData<AppState> = MutableLiveData(),
private val repository: RepositoryImpl = RepositoryImpl()
) :
    ViewModel() {

    fun getData(): LiveData<AppState> {
        return liveData
    }

    fun getWeather() {
        Thread {
            liveData.postValue(AppState.Loading)

            if ((0..10).random() > 2) {
                liveData.postValue(AppState.Success(repository.getWeatherFromServer()))
            } else {
                liveData.postValue(AppState.Error(IllegalAccessError("AAAAAAAAAAAAAAAAA!!!!!")))
            }

        }.start()
    }
}