package com.example.weatherapp.utils

import android.view.View
import com.example.weatherapp.repository.City
import com.example.weatherapp.repository.Weather
import com.example.weatherapp.repository.dto.FactDTO
import com.example.weatherapp.repository.dto.WeatherDTO
import com.example.weatherapp.repository.getDefaultCity
import com.example.weatherapp.room.HistoryEntity
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

const val KEY_BUNDLE_WEATHER = "key"
const val FORMAT_DATE = "E, dd MMMM H:m"
const val KEY_BUNDLE_LAT = "lat123"
const val KEY_BUNDLE_LON = "lon123"
const val YANDEX_DOMAIN = "https://api.weather.yandex.ru/"
const val YANDEX_ENDPOINT = "v2/informers?"
const val YANDEX_API_KEY = "X-Yandex-API-Key"
const val KEY_WAVE_SERVICE_BROADCAST = "my_action_way"
const val KEY_BUNDLE_SERVICE_BROADCAST_WEATHER = "weather_s_b"
const val HISTORY_TABLE = "history_table"


fun View.showSnackBar(text: String, length: Int = Snackbar.LENGTH_LONG) {
    Snackbar.make(this, text, length).show()
}

fun View.showSnackBarError(
    text: String,
    actionText: String,
    action: (View) -> Unit,
    length: Int = Snackbar.LENGTH_LONG
) {
    Snackbar.make(this, text, length)
        .setAction(actionText, action).show()
}

fun Date.formatDate(): String = SimpleDateFormat(FORMAT_DATE, Locale.getDefault()).format(this)

fun convertDtoToModel(weatherDTO: WeatherDTO): Weather {
    val fact: FactDTO = weatherDTO.factDTO
    return (Weather(getDefaultCity(), fact.temp, fact.feelsLike, fact.icon))
}

fun String.addDegree(): String = this + "\u00B0"

fun convertHistoryEntityToWeather(entityList: List<HistoryEntity>): List<Weather> {
    return entityList.map {
        Weather(City(it.city, 0.0, 0.0), it.temperature, it.feelsLike, it.icon)
    }
}

fun convertWeatherToEntity(weather: Weather): HistoryEntity {
    return HistoryEntity(
        0,
        weather.city.cityName,
        weather.temperature,
        weather.feelsLike,
        weather.icon
    )
}

fun factToWeather(weatherDTO: FactDTO): Weather {
    return Weather(City(), weatherDTO.temp, weatherDTO.feelsLike, weatherDTO.icon)
}