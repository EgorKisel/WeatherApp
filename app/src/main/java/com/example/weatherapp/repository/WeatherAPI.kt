package com.example.weatherapp.repository

import com.example.weatherapp.repository.dto.WeatherDTO
import com.example.weatherapp.utils.YANDEX_API_KEY
import com.example.weatherapp.utils.YANDEX_ENDPOINT
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface WeatherAPI {

    @GET(YANDEX_ENDPOINT)
    fun getWeather(
        @Header(YANDEX_API_KEY) apiKey: String,
        @Query("lat") lat: Double,
        @Query("lon") lon: Double
    ): Call<WeatherDTO>
}