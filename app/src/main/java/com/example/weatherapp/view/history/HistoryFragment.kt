package com.example.weatherapp.view.history

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapp.R
import com.example.weatherapp.databinding.FragmentHistoryBinding
import com.example.weatherapp.repository.Weather
import com.example.weatherapp.utils.KEY_BUNDLE_WEATHER
import com.example.weatherapp.utils.showSnackBarError
import com.example.weatherapp.view.details.DetailsFragment
import com.example.weatherapp.viewmodel.AppState
import com.example.weatherapp.viewmodel.HistoryViewModel

class HistoryFragment : Fragment(), ClickHistory {

    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HistoryViewModel by lazy {
        ViewModelProvider(this)[HistoryViewModel::class.java]
    }
    private val adapter: HistoryAdapter by lazy {
        HistoryAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        adapter.setOnNoteClickListener(this)
        binding.historyFragmentRecyclerView.adapter = adapter
        viewModel.getData().observe(viewLifecycleOwner) { renderData(it) }
        viewModel.getAllHistory()
    }

    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Error -> {
                with(binding) {
                    historyFragmentRecyclerView.visibility = View.VISIBLE
                    loadingLayout.visibility = View.GONE
                    historyFragmentRecyclerView.showSnackBarError(
                        getString(R.string.error),
                        getString(R.string.reload),
                        {
                            viewModel.getAllHistory()
                        })
                }
            }
            AppState.Loading -> with(binding) {
                historyFragmentRecyclerView.visibility = View.GONE
                loadingLayout.visibility = View.VISIBLE
            }
            is AppState.Success -> {
                with(binding) {
                    historyFragmentRecyclerView.visibility = View.VISIBLE
                    loadingLayout.visibility = View.GONE
                }
                if (appState.weatherList.isEmpty()) {
                    Toast.makeText(context, "Empty", Toast.LENGTH_SHORT).show()
                }
                adapter.setData(appState.weatherList)
            }
        }
    }

    override fun selectWeather(weather: Weather) {
        activity?.supportFragmentManager?.let {
            it.beginTransaction()
                .replace(R.id.container, DetailsFragment.newInstance(Bundle().apply {
                    putParcelable(KEY_BUNDLE_WEATHER, weather)
                })).addToBackStack("").commitAllowingStateLoss()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_history, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        viewModel.deleteHistory()
        return super.onOptionsItemSelected(item)
    }

    override fun deleteHistory() {
        viewModel.deleteHistory()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getAllHistory()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            HistoryFragment()
    }
}