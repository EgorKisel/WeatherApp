package com.example.weatherapp.repository

import android.os.Handler
import android.os.Looper
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL
import java.util.stream.Collectors
import javax.net.ssl.HttpsURLConnection

class WeatherLoader(private val onServerResponseListener: OnServerResponse) {

    fun loadWeather(lat: Double, lon: Double) {

//        val uri = URL("https://api.weather.yandex.ru/v2/forecast?lat=${lat}&lon=${lon}")
//        val urlConnection: HttpsURLConnection = (uri.openConnection() as HttpsURLConnection).apply {
//            //readTimeout = 1000
//            connectTimeout = 1000 // set под капотом
//            //val r= readTimeout // get под капотом
//            readTimeout = 1000 // set под капотом
//            addRequestProperty("X-Yandex-API-Key", "345f15dc-fd28-493d-8bfa-721e72537847")
//        }

        val urlText = "https://api.weather.yandex.ru/v2/informers?lat=$lat&lon=$lon"
        val uri = URL(urlText)
        val urlConnection: HttpsURLConnection =
            (uri.openConnection() as HttpsURLConnection).apply {
                connectTimeout = 1000 // set под капотом
                //val r= readTimeout // get под капотом
                readTimeout = 1000 // set под капотом
                addRequestProperty("X-Yandex-API-Key", "345f15dc-fd28-493d-8bfa-721e72537847")
            }

        Thread {
            try {
                val headers = urlConnection.headerFields
                val responseCode = urlConnection.responseCode
                val buffer = BufferedReader(InputStreamReader(urlConnection.inputStream))
                //val result = (buffer)
                val weatherDTO: WeatherDTO = Gson().fromJson(buffer, WeatherDTO::class.java)
                Handler(Looper.getMainLooper()).post {
                    onServerResponseListener.onResponse(weatherDTO)
                }
            } catch (e: Exception) {
                // "что-то пошло не так" Snackbar?
            }
            // disconnect() finally?


        }.start()

//        try {
//            Thread {
//
//                //Thread.sleep(1000)
//                val headers = urlConnection.headerFields
//                val responseCode = urlConnection.responseCode
//                val responseMessage = urlConnection.responseMessage
//                val buffer = BufferedReader(InputStreamReader(urlConnection.inputStream))
//                val response = getLines(buffer)
//                val weatherDTO: WeatherDTO = Gson().fromJson(buffer, WeatherDTO::class.java)
//                Handler(Looper.getMainLooper()).post {
//                    onServerResponseListener.onResponse(weatherDTO)
//                }
//            }.start()
//        } catch (e: MalformedURLException) {
//            Log.e("@@@", "Fail URI", e)
//            e.printStackTrace()
//        }
    }

    private fun getLines(buffer: BufferedReader): String {
        return buffer.lines().collect(Collectors.joining("\n"))
    }
}