package com.example.weatherapp.repository

import com.example.weatherapp.repository.dto.WeatherDTO

fun interface OnServerResponse {
    fun onResponse(weatherDTO: WeatherDTO)
}