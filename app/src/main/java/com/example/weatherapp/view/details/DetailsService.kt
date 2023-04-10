package com.example.weatherapp.view.details

import android.app.IntentService
import android.content.Intent
import android.util.Log
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.weatherapp.BuildConfig
import com.example.weatherapp.repository.dto.WeatherDTO
import com.example.weatherapp.utils.*
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class DetailsService(name: String = "") : IntentService(name) {
    override fun onHandleIntent(intent: Intent?) {
        Log.d("@@@", "DetailsService is working")
        intent?.let {
            val lat = it.getDoubleExtra(KEY_BUNDLE_LAT, 0.0)
            val lon = it.getDoubleExtra(KEY_BUNDLE_LON, 0.0)

            val urlText = "$YANDEX_DOMAIN${YANDEX_PATH}lat=$lat&lon=$lon"
            val uri = URL(urlText)
            val urlConnection: HttpsURLConnection =
                (uri.openConnection() as HttpsURLConnection).apply {
                    connectTimeout = 1000
                    readTimeout = 1000
                    addRequestProperty(YANDEX_API_KEY, BuildConfig.WEATHER_API_KEY)
                }
            val headers = urlConnection.headerFields
            val responseCode = urlConnection.responseCode
            val responseMessage = urlConnection.responseMessage
            val serverSide = 500..599
            val clientSide = 400..499
            val responseOk = 200..299

            when (responseCode) {
                in serverSide -> {
                    Log.d("serverSide", "$responseCode: Error on server side")
                }
                in clientSide -> {
                    Log.d("clientSide", "$responseCode: Error on client side")
                }
                in responseOk -> {
                    val buffer = BufferedReader(InputStreamReader(urlConnection.inputStream))
                    val weatherDTO: WeatherDTO = Gson().fromJson(buffer, WeatherDTO::class.java)
                    val message = Intent(KEY_WAVE_SERVICE_BROADCAST)
                    message.putExtra(KEY_BUNDLE_SERVICE_BROADCAST_WEATHER, weatherDTO)
                    //sendBroadcast(message)
                    LocalBroadcastManager.getInstance(this).sendBroadcast(message)
                }
                else -> {
                    Log.d("@@@", "$responseCode: Error")
                }
            }
        }
    }
}