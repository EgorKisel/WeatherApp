package com.example.weatherapp.viewmodel

import com.example.weatherapp.repository.Weather

sealed class AppState {
    data class Success(val weatherData: Weather): AppState()
    object Loading: AppState()
    data class Error(val error: Throwable): AppState()
}
