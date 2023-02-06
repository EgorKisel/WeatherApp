package com.example.weatherapp.repository

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class Hours(val hour: Int = Calendar.HOUR_OF_DAY, val temp: Int = 10) : Parcelable

fun getHoursList(): List<Hours> {
    return listOf(
        Hours(Date().hours,Random().nextInt(10)-5),
        Hours(Date().hours,Random().nextInt(10)-5),
        Hours(Date().hours,Random().nextInt(10)-5),
        Hours(Date().hours,Random().nextInt(10)-5),
        Hours(Date().hours,Random().nextInt(10)-5),
        Hours(Date().hours,Random().nextInt(10)-5),
        Hours(Date().hours,Random().nextInt(10)-5),
        Hours(Date().hours,Random().nextInt(10)-5),
        Hours(Date().hours,Random().nextInt(10)-5),
        Hours(Date().hours,Random().nextInt(10)-5),
        Hours(Date().hours,Random().nextInt(10)-5),
        Hours(Date().hours,Random().nextInt(10)-5),
        Hours(Date().hours,Random().nextInt(10)-5),
        Hours(Date().hours,Random().nextInt(10)-5),
        Hours(Date().hours,Random().nextInt(10)-5),
        Hours(Date().hours,Random().nextInt(10)-5),
        Hours(Date().hours,Random().nextInt(10)-5),
        Hours(Date().hours,Random().nextInt(10)-5),
        Hours(Date().hours,Random().nextInt(10)-5),
        Hours(Date().hours,Random().nextInt(10)-5),
        Hours(Date().hours,Random().nextInt(10)-5),
        Hours(Date().hours,Random().nextInt(10)-5),
        Hours(Date().hours,Random().nextInt(10)-5),
        Hours(Date().hours,Random().nextInt(10)-5)
    )
}