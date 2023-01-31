package com.example.weatherapp.repository

interface Repository {
    fun getWeatherFromServer(): Weather
    fun getWeatherFromLocalStorage(): Weather
}