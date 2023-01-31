package com.example.weatherapp.repository

class RepositoryImpl: Repository {
    override fun getWeatherFromServer(): Weather {
        Thread.sleep(2000L)
        return Weather()
    }

    override fun getWeatherFromLocalStorage(): Weather {
        Thread.sleep(1000L)
        return Weather()
    }
}