package com.example.weatherapp.view.weatherlist

import com.example.weatherapp.repository.Weather

interface OnItemListClickListener {
    fun onItemClick(weather: Weather)
}