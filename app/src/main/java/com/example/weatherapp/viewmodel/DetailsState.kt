package com.example.weatherapp.viewmodel

import com.example.weatherapp.repository.Weather

sealed class DetailsState {
    object Loading: DetailsState()
    data class Success(val weather: Weather): DetailsState()
    data class Error(val error: Throwable): DetailsState()
}