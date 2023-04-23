package com.example.weatherapp.repository

interface LocalRepository {
    fun getAllHistory(): List<Weather>
    fun saveEntity(weather: Weather)
    fun deleteHistory()
}