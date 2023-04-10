package com.example.weatherapp.view.details

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.databinding.WeekItemBinding
import com.example.weatherapp.repository.dto.ForecastDTO
import java.text.SimpleDateFormat

class AdapterWeek: RecyclerView.Adapter<AdapterWeek.HolderWeek>() {

    private var forecastData: List<ForecastDTO> = listOf()

    fun setForecastData(forestsDataNew: ArrayList<ForecastDTO>) {
        this.forecastData = forestsDataNew
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderWeek {
        val binding = WeekItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HolderWeek(binding.root)
    }

    override fun onBindViewHolder(holder: HolderWeek, position: Int) {
        holder.bind(forecastData[position])
    }

    override fun getItemCount() = forecastData.size

    class HolderWeek(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(forecasts: ForecastDTO) {
            val binding = WeekItemBinding.bind(itemView)
            binding.weekDay.text = SimpleDateFormat("EEEE").format(forecasts.date)
            /*binding.weatherWeekMax.text = forecasts.partsDTO.tempMax.toString() + "/"
            binding.weatherWeekMin.text = forecasts.partsDTO.tempMin.toString()*/
        }
    }
}