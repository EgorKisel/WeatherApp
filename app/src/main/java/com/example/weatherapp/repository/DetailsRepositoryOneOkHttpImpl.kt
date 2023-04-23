package com.example.weatherapp.repository

import com.example.weatherapp.BuildConfig
import com.example.weatherapp.repository.dto.WeatherDTO
import com.example.weatherapp.utils.YANDEX_API_KEY
import com.example.weatherapp.utils.YANDEX_DOMAIN
import com.example.weatherapp.utils.YANDEX_ENDPOINT
import com.example.weatherapp.utils.convertDtoToModel
import com.example.weatherapp.viewmodel.DetailsViewModel
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.Request

class DetailsRepositoryOneOkHttpImpl : DetailsRepositoryOne {
    override fun getWeatherDetails(city: City, callback: DetailsViewModel.Callback) {
        val client = OkHttpClient()
        val builder = Request.Builder()
        builder.addHeader(YANDEX_API_KEY, BuildConfig.WEATHER_API_KEY)
        builder.url("$YANDEX_DOMAIN${YANDEX_ENDPOINT}lat=${city.lat}&lon=${city.lon}")
        val request = builder.build()
        val call = client.newCall(request)
        Thread {
            val response = call.execute()
            if (response.isSuccessful) {
                val serverResponse = response.body!!.string()
                val weatherDTO: WeatherDTO = Gson().fromJson(serverResponse, WeatherDTO::class.java)
                val weather = convertDtoToModel(weatherDTO)
                weather.city = city
                callback.onResponse(weatherDTO)
            } else {
                //
            }
        }.start()
    }
}