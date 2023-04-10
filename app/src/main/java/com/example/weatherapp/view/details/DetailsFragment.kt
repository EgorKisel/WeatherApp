package com.example.weatherapp.view.details

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherapp.R
import com.example.weatherapp.databinding.NewFragmentDetailsBinding
import com.example.weatherapp.repository.OnServerResponse
import com.example.weatherapp.repository.Weather
import com.example.weatherapp.repository.dto.WeatherDTO
import com.example.weatherapp.repository.WeatherLoader
import com.example.weatherapp.utils.*
import java.util.*

class DetailsFragment : Fragment(), OnServerResponse {

    private var _binding: NewFragmentDetailsBinding? = null
    private val binding: NewFragmentDetailsBinding get() = _binding!!
    private val adapterHour = AdapterHour()
    private val adapterWeek = AdapterWeek()
    lateinit var currentCityName: String

    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            intent?.let { intent ->
                intent.getParcelableExtra<WeatherDTO>(KEY_BUNDLE_SERVICE_BROADCAST_WEATHER)
                    ?.let { weatherDTO ->
                        onResponse(weatherDTO)
                    }
            }
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = NewFragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //val weather: Weather = requireArguments().getParcelable(KEY_BUNDLE_WEATHER)!!

        LocalBroadcastManager.getInstance(requireContext()).registerReceiver(
            receiver, IntentFilter(
                KEY_WAVE_SERVICE_BROADCAST
            )
        )

        arguments?.getParcelable<Weather>(KEY_BUNDLE_WEATHER)?.let {
            currentCityName = it.city.cityName
            //WeatherLoader(this@DetailsFragment).loadWeather(it.city.lat, it.city.lon)

            requireActivity().startService(
                Intent(
                    requireContext(),
                    DetailsService::class.java
                ).apply {
                    putExtra(KEY_BUNDLE_LAT, it.city.lat)
                    putExtra(KEY_BUNDLE_LON, it.city.lon)
                })
        }
        val layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        binding.listHour.adapter = adapterHour
        binding.listWeek.adapter = adapterWeek
        binding.listHour.layoutManager = layoutManager

    }

    private fun renderData(weather: WeatherDTO) {
        with(binding) {
            dataText.text = Date().formatDate()
            weatherIcon.background = resources.getDrawable(R.drawable.sun)
            weatherText.text = weather.factDTO.temp.toString()
            conditionText.text = weather.factDTO.condition
            feelsLikeText.text =
                resources.getString(R.string.feelsLike) + " " + weather.factDTO.feelsLike.toString()
            cityName.text = currentCityName
        }
        //adapterHour.setWeatherData(weather.forecastDTO.hours)
        // adapterWeek.setForecastData(weather.forecastDTO.week)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        LocalBroadcastManager.getInstance(requireContext()).unregisterReceiver(receiver)
    }

    companion object {

        @JvmStatic
        fun newInstance(bundle: Bundle): DetailsFragment {
            val fragment = DetailsFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onResponse(weatherDTO: WeatherDTO) {
        renderData(weatherDTO)
    }
}