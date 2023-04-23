package com.example.weatherapp.view.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import coil.ImageLoader
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.example.weatherapp.databinding.NewFragmentDetailsBinding
import com.example.weatherapp.repository.City
import com.example.weatherapp.repository.Weather
import com.example.weatherapp.repository.dto.WeatherDTO
import com.example.weatherapp.utils.*
import com.example.weatherapp.viewmodel.DetailsState
import com.example.weatherapp.viewmodel.DetailsViewModel
import java.util.*

class DetailsFragment : Fragment() {

    private var _binding: NewFragmentDetailsBinding? = null
    private val binding: NewFragmentDetailsBinding get() = _binding!!
    private val adapterHour = AdapterHour()
    private val adapterWeek = AdapterWeek()
    lateinit var currentCityName: String
    private lateinit var weatherBundle: Weather
    private val viewModel: DetailsViewModel by lazy {
        ViewModelProvider(this)[DetailsViewModel::class.java]
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

        viewModel.getLivedata().observe(viewLifecycleOwner) {
            renderData(it)
        }

        arguments?.getParcelable<Weather>(KEY_BUNDLE_WEATHER)?.let {
            viewModel.getWeather(it.city)
        }

        weatherBundle = arguments?.getParcelable(KEY_BUNDLE_WEATHER) ?: Weather()

        val layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        binding.listHour.adapter = adapterHour
        binding.listWeek.adapter = adapterWeek
        binding.listHour.layoutManager = layoutManager

    }

    private fun renderData(detailsState: DetailsState) {
        when (detailsState) {
            is DetailsState.Error -> {}
            DetailsState.Loading -> {}
            is DetailsState.Success -> {
                setWeather(detailsState.weatherDTO)
                binding.weatherIcon.loadSvg("https://yastatic.net/weather/i/icons/funky/dark/${detailsState.weatherDTO.factDTO.icon}.svg")
            }
        }

    }

    private fun setWeather(weatherDTO: WeatherDTO) {
        val city = weatherBundle.city
        saveCity(city, factToWeather(weatherDTO.factDTO))
        with(binding)
        {
            weatherDTO.factDTO?.apply {
                cityName.text = city.cityName
                feelsLikeText.text = feelsLike.toString().addDegree()
                conditionText.text = condition
                weatherText.text = temp.toString().addDegree()
                dataText.text = Date().formatDate()
                weatherIcon.loadSvg(icon!!)
                windSpeed2.text = "$windSpeed м/с"
                PressureMm.text = "$pressureMm мм рт"
                Humidity.text = "$humidity%"
                Season.text = season
                sunrise.text = weatherDTO.forecastDTO.sunrise
                sunset.text = weatherDTO.forecastDTO.sunset
            }
        }
    }

    private fun saveCity(city: City, weather: Weather) {
        with(weather) {
            viewModel.saveCityToDB(Weather(city, temperature, feelsLike, icon))
        }
    }

    private fun ImageView.loadSvg(url: String) {
        val imageLoader =
            ImageLoader.Builder(this.context).components { add(SvgDecoder.Factory()) }
                .build()
        val request = ImageRequest.Builder(this.context)
            .crossfade(true)
            .crossfade(500)
            .data(url)
            .target(this)
            .build()
        imageLoader.enqueue(request)
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
}