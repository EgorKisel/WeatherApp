package com.example.weatherapp.repository

import android.os.Handler
import android.os.Looper
import android.util.Log
import com.example.weatherapp.BuildConfig
import com.example.weatherapp.repository.dto.WeatherDTO
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL
import java.util.stream.Collectors
import javax.net.ssl.HttpsURLConnection

class WeatherLoader(private val onServerResponseListener: OnServerResponse) {

    fun loadWeather(lat: Double, lon: Double) {

        val urlText = "https://api.weather.yandex.ru/v2/informers?lat=$lat&lon=$lon"
        val uri = URL(urlText)
        val urlConnection: HttpsURLConnection =
            (uri.openConnection() as HttpsURLConnection).apply {
                connectTimeout = 1000 // set под капотом
                //val r= readTimeout // get под капотом
                readTimeout = 1000 // set под капотом
                addRequestProperty("X-Yandex-API-Key", BuildConfig.WEATHER_API_KEY)
            }

        Thread {
            try {
                val headers = urlConnection.headerFields
                val responseCode = urlConnection.responseCode
                val buffer = BufferedReader(InputStreamReader(urlConnection.inputStream))
                val weatherDTO: WeatherDTO = Gson().fromJson(buffer, WeatherDTO::class.java)
                Handler(Looper.getMainLooper()).post {
                    onServerResponseListener.onResponse(weatherDTO)
                }
            } catch (e: Exception) {
                Log.e("", "Fail URL", e)
                e.printStackTrace()
            } finally {
                urlConnection.disconnect()
            }


        }.start()
    }

    private fun getLines(buffer: BufferedReader): String {
        return buffer.lines().collect(Collectors.joining("\n"))
    }
}