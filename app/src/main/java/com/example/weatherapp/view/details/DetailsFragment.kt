package com.example.weatherapp.view.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherapp.R
import com.example.weatherapp.databinding.NewFragmentDetailsBinding
import com.example.weatherapp.repository.Weather
import com.example.weatherapp.utils.KEY_BUNDLE_WEATHER
import java.text.SimpleDateFormat

class DetailsFragment : Fragment() {

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val weather: Weather = requireArguments().getParcelable(KEY_BUNDLE_WEATHER)!!
        val layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        binding.listHour.adapter = adapterHour
        binding.listWeek.adapter = adapterWeek
        binding.listHour.layoutManager = layoutManager

        renderData(weather)
    }

    private fun renderData(weather: Weather) {
        with(binding) {
            cityName.text = weather.city.name
            dataText.text =
                SimpleDateFormat(getString(R.string.time_format)).format(weather.time)
            weatherIcon.background = resources.getDrawable(R.drawable.sun)
            weatherText.text = weather.temperature.toString()
            conditionText.text = weather.condition
            feelsLikeText.text =
                resources.getString(R.string.feelsLike) + " " + weather.feelsLike.toString()
        }
        adapterHour.setWeatherData(weather.forecastList[0].hours)
        adapterWeek.setForecastData(weather.forecastList)
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