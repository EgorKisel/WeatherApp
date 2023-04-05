package com.example.weatherapp.repository

data class Forecasts(
    val date: String,
    val hours: ArrayList<Hours> = arrayListOf(),
    val parts: Parts
)

data class Parts(
    var evening: Evening,
    var morning: Morning,
    var night: Night,
    var day: Day,
)

data class Day(
    val temp_min: Int,
    val temp_max: Int
)

data class Night(
    var temp_min: Int,
    var temp_max: Int
)

data class Morning(
    val temp_min: Int,
    val temp_max: Int
)

data class Evening(
    val temp_min: Int,
    val temp_max: Int
)
