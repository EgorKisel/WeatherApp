package com.example.weatherapp.repository

data class Weather(val city: City = getDefaultCity(), val temperature: Int = 0, val feelsLike: Int = 0)


data class City(val name: String, val lat: Double, val lon: Double)

fun getDefaultCity() = City("Moscow", 55.75, 37.61)
