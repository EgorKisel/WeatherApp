package com.example.weatherapp.utils

import android.view.View
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

const val KEY_BUNDLE_WEATHER = "key"
const val FORMAT_DATE = "E, dd MMMM H:m"
const val KEY_BUNDLE_LAT = "lat123"
const val KEY_BUNDLE_LON = "lon123"
const val YANDEX_DOMAIN = "https://api.weather.yandex.ru/"
const val YANDEX_PATH = "v2/informers?"
const val YANDEX_API_KEY = "X-Yandex-API-Key"
const val KEY_WAVE_SERVICE_BROADCAST = "my_action_way"
const val KEY_BUNDLE_SERVICE_BROADCAST_WEATHER = "weather_s_b"

class Utils {
}

fun View.showSnackBar(text: String, length: Int = Snackbar.LENGTH_LONG) {
    Snackbar.make(this, text, length).show()
}

fun Date.formatDate(): String = SimpleDateFormat(FORMAT_DATE, Locale.getDefault()).format(this)