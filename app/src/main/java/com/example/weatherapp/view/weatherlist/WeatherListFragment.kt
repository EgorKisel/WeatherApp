package com.example.weatherapp.view.weatherlist

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapp.R
import com.example.weatherapp.databinding.FragmentWeatherListBinding
import com.example.weatherapp.repository.Weather
import com.example.weatherapp.utils.KEY_BUNDLE_WEATHER
import com.example.weatherapp.utils.showSnackBar
import com.example.weatherapp.view.details.DetailsFragment
import com.example.weatherapp.viewmodel.AppState
import com.example.weatherapp.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.fragment_details.*

class WeatherListFragment : Fragment(), OnItemListClickListener {

    private var _binding: FragmentWeatherListBinding? = null
    private val binding: FragmentWeatherListBinding get() = _binding!!
    private val adapter = WeatherListAdapter(this)
    private var isRussian = true
    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this)[MainViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWeatherListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val observer = Observer<AppState> { data -> renderData(data) }
        binding.recyclerView.adapter = adapter

        activity?.let {
            isRussian = it.getPreferences(Context.MODE_PRIVATE).getBoolean(Companion.IS_WORLD_KEY, true)
        }

        viewModel.apply {
            getData().observe(viewLifecycleOwner, observer)
            if (isRussian) {
                getWeatherRussia()
            } else {
                getWeatherWorld()
            }
        }
        binding.floatingActionButton.setOnClickListener {
            changeWeatherData()
        }
    }

    private fun changeWeatherData() {
        viewModel.apply {
            if (!isRussian) {
                getWeatherRussia()
                binding.floatingActionButton.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.ic_russia
                    )
                )
            } else {
                getWeatherWorld()
                binding.floatingActionButton.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.ic_earth
                    )
                )
            }
        }.also {
            isRussian = !isRussian
            activity?.let {
                it.getPreferences(Context.MODE_PRIVATE).edit().putBoolean(Companion.IS_WORLD_KEY, isRussian)
                    .apply()
            }
        }
    }

    private fun renderData(data: AppState) {
        when (data) {
            is AppState.Error -> {
                binding.loadingLayout.visibility = View.GONE
                loadingLayout.showSnackBar("NOT WORKING")
            }
            is AppState.Loading -> {
                binding.loadingLayout.visibility = View.VISIBLE
            }
            is AppState.Success -> {
                binding.loadingLayout.visibility = View.GONE
                adapter.setData(data.weatherList)
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {

        @JvmStatic
        fun newInstance() = WeatherListFragment()
        const val IS_WORLD_KEY = "list_of_towns_key"
    }

    override fun onItemClick(weather: Weather) {
        val bundle = Bundle()
        bundle.putParcelable(KEY_BUNDLE_WEATHER, weather)
        requireActivity().supportFragmentManager.beginTransaction()
            .add(R.id.container, DetailsFragment.newInstance(bundle)).addToBackStack("")
            .commit()
    }
}