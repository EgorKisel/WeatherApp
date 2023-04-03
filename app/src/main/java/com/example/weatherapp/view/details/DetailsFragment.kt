package com.example.weatherapp.view.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherapp.R
import com.example.weatherapp.databinding.NewFragmentDetailsBinding
import com.example.weatherapp.repository.OnServerResponse
import com.example.weatherapp.repository.Weather
import com.example.weatherapp.repository.WeatherDTO
import com.example.weatherapp.repository.WeatherLoader
import com.example.weatherapp.utils.KEY_BUNDLE_WEATHER
import java.text.SimpleDateFormat
import java.util.*

class DetailsFragment : Fragment(), OnServerResponse {

    private var _binding: NewFragmentDetailsBinding? = null
    private val binding: NewFragmentDetailsBinding get() = _binding!!
    private val adapterHour = AdapterHour()
    private val adapterWeek = AdapterWeek()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = NewFragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    lateinit var currentCityName: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //val weather: Weather = requireArguments().getParcelable(KEY_BUNDLE_WEATHER)!!
        arguments?.getParcelable<Weather>(KEY_BUNDLE_WEATHER)?.let {
            currentCityName = it.city.name
            WeatherLoader(this@DetailsFragment).loadWeather(it.city.lat, it.city.lon)
        }
        val layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        binding.listHour.adapter = adapterHour
        binding.listWeek.adapter = adapterWeek
        binding.listHour.layoutManager = layoutManager

    }

    private fun renderData(weather: WeatherDTO) {
        with(binding) {
            dataText.text =
                SimpleDateFormat(getString(R.string.time_format), Locale.getDefault()).format(this@DetailsFragment)
            weatherIcon.background = resources.getDrawable(R.drawable.sun)
            weatherText.text = weather.factDTO.temp.toString()
            conditionText.text = weather.factDTO.condition
            feelsLikeText.text =
                resources.getString(R.string.feelsLike) + " " + weather.factDTO.feelsLike.toString()
        }
        adapterHour.setWeatherData(weather.forecastDTO[0].hours)
        adapterWeek.setForecastData(weather.forecastDTO)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
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