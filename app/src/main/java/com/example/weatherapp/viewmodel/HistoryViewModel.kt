package com.example.weatherapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherapp.MyApp.Companion.getHistoryDao
import com.example.weatherapp.repository.LocalRepositoryRoomImpl

class HistoryViewModel(
    private val livedata: MutableLiveData<AppState> = MutableLiveData(),
    private val repository: LocalRepositoryRoomImpl = LocalRepositoryRoomImpl(getHistoryDao())
) : ViewModel() {

    fun getData(): LiveData<AppState> = livedata

    fun getAllHistory() {
        livedata.value = AppState.Loading
        Thread {
            livedata.postValue(AppState.Success(repository.getAllHistory()))
        }.start()
    }

    fun deleteHistory() {
        Thread {
            repository.deleteHistory()
            livedata.postValue(AppState.Success(repository.getAllHistory()))
        }.start()
    }
}