package com.example.weatherapp.viewmodel

import com.example.weatherapp.repository.dto.WeatherDTO

sealed class DetailsState {
    object Loading: DetailsState()
    data class Success(val weatherDTO: WeatherDTO): DetailsState()
    data class Error(val error: Throwable): DetailsState()
}