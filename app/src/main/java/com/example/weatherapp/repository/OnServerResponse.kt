package com.example.weatherapp.repository

fun interface OnServerResponse {
    fun onResponse(weatherDTO: WeatherDTO)
}