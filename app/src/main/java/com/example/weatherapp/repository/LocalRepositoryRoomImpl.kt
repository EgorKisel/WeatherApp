package com.example.weatherapp.repository

import com.example.weatherapp.room.HistoryDao
import com.example.weatherapp.utils.convertHistoryEntityToWeather
import com.example.weatherapp.utils.convertWeatherToEntity

class LocalRepositoryRoomImpl(private val localDataSource: HistoryDao): LocalRepository {
    override fun getAllHistory(): List<Weather> {
        return convertHistoryEntityToWeather(localDataSource.getAll())
    }

    override fun saveEntity(weather: Weather) {
        localDataSource.insert(convertWeatherToEntity(weather))
    }

    override fun deleteHistory() {
        localDataSource.deleteAll()
    }

}