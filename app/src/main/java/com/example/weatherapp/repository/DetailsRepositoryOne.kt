package com.example.weatherapp.repository

import com.example.weatherapp.viewmodel.DetailsViewModel

interface DetailsRepositoryOne {
    fun getWeatherDetails(city: City, callback: DetailsViewModel.Callback)
}