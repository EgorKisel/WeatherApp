package com.example.weatherapp.repository

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class Forecasts(
    val date: Date = Date(),
    val tempMin: Int = 10,
    val tempMax: Int = 15,
    val hours: List<Hours> = listOf()
) : Parcelable

fun getForecastsList(): List<Forecasts> {
    return listOf(
        Forecasts(Date(), Random().nextInt(20)-10, Random().nextInt(20)-5, getHoursList()),
        Forecasts(Date(), Random().nextInt(20)-10, Random().nextInt(20)-5, getHoursList()),
        Forecasts(Date(), Random().nextInt(20)-10, Random().nextInt(20)-5, getHoursList()),
        Forecasts(Date(), Random().nextInt(20)-10, Random().nextInt(20)-5, getHoursList()),
        Forecasts(Date(), Random().nextInt(20)-10, Random().nextInt(20)-5, getHoursList()),
        Forecasts(Date(), Random().nextInt(20)-10, Random().nextInt(20)-5, getHoursList()),
        Forecasts(Date(), Random().nextInt(20)-10, Random().nextInt(20)-5, getHoursList()))
}
