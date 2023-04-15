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
import com.example.weatherapp.R
import com.example.weatherapp.databinding.NewFragmentDetailsBinding
import com.example.weatherapp.repository.Weather
import com.example.weatherapp.utils.KEY_BUNDLE_WEATHER
import com.example.weatherapp.utils.formatDate
import com.example.weatherapp.viewmodel.DetailsState
import com.example.weatherapp.viewmodel.DetailsViewModel
import java.util.*

class DetailsFragment : Fragment() {

    private var _binding: NewFragmentDetailsBinding? = null
    private val binding: NewFragmentDetailsBinding get() = _binding!!
    private val adapterHour = AdapterHour()
    private val adapterWeek = AdapterWeek()
    lateinit var currentCityName: String
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
                val weather = detailsState.weather
                with(binding) {
                    dataText.text = Date().formatDate()
                    weatherIcon.loadSvg("https://yastatic.net/weather/i/icons/funky/dark/${weather.icon}.svg")
                    //weatherIcon.background = resources.getDrawable(R.drawable.sun)
                    weatherText.text = weather.temperature.toString()
                    //conditionText.text = weather.
                    feelsLikeText.text =
                        resources.getString(R.string.feelsLike) + " " + weather.feelsLike.toString()
                    cityName.text = weather.city.cityName
                }
                //adapterHour.setWeatherData(weather.forecastDTO.hours)
                // adapterWeek.setForecastData(weather.forecastDTO.week)
            }
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