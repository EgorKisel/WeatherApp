package com.example.weatherapp.repository

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class Weather(
    var city: City = getDefaultCity(),
    val temperature: Int = 0,
    val feelsLike: Int = 0,
    val icon: String = "bkn_n"
) : Parcelable

@Parcelize
data class City(val cityName: String = "Москва", val lat: Double = 55.0, val lon: Double = 55.0) : Parcelable

fun getDefaultCity() = City("Moscow", 55.75, 37.61)
fun getTemp(): Int {
    return Random().nextInt(20) - 5
}

fun getWorldCities(): List<Weather> {
    return listOf(
        Weather(City("London", 51.5085300, -0.1257400), getTemp()),
        Weather(City("Tokyo", 35.6895000, 139.6917100), getTemp()),
        Weather(City("Paris", 48.8534100, 2.3488000), getTemp()),
        Weather(City("Berlin", 52.52000659999999, 13.404953999999975), getTemp()),
        Weather(City("Rome", 41.9027835, 12.496365500000024), getTemp()),
        Weather(City("Minsk", 53.90453979999999, 27.561524400000053), getTemp()),
        Weather(City("Istanbul", 41.0082376, 28.97835889999999), getTemp()),
        Weather(City("Washington", 38.9071923, -77.03687070000001), getTemp()),
        Weather(City("Kyiv", 50.4501, 30.523400000000038), getTemp()),
        Weather(City("Beijing", 39.90419989999999, 116.40739630000007), getTemp())
    )
}

fun getRussianCities(): List<Weather> {
    return listOf(
        Weather(City("Moscow", 55.755826, 37.617299900000035), getTemp()),
        Weather(City("Saint Petersburg", 59.9342802, 30.335098600000038), getTemp()),
        Weather(City("Novosibirsk", 55.00835259999999, 82.93573270000002), getTemp()),
        Weather(City("Ekaterinburg", 56.83892609999999, 60.60570250000001), getTemp()),
        Weather(City("Nizhny Novgorod", 56.2965039, 43.936059), getTemp()),
        Weather(City("Kazan", 55.8304307, 49.06608060000008), getTemp()),
        Weather(City("Chelyabinsk", 55.1644419, 61.4368432), getTemp()),
        Weather(City("Omsk", 54.9884804, 73.32423610000001), getTemp()),
        Weather(City("Rostov-on-Don", 47.2357137, 39.701505), getTemp()),
        Weather(City("Ufa", 54.7387621, 55.972055400000045), getTemp()),
        Weather(City("Sochi", 43.60028, 39.73789), getTemp())
    )
}