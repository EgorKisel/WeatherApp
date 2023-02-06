package com.example.weatherapp.view.details

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.R
import com.example.weatherapp.databinding.HourItemBinding
import com.example.weatherapp.repository.Hours
import java.text.SimpleDateFormat

class AdapterHour : RecyclerView.Adapter<AdapterHour.HolderHour>() {

    private var hoursData: List<Hours> = listOf()

    fun setWeatherData(hoursDataNew: List<Hours>) {
        this.hoursData = hoursDataNew
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderHour {
        val binding = HourItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HolderHour(binding.root)
    }

    override fun onBindViewHolder(holder: HolderHour, position: Int) {
        holder.bind(hoursData[position])
    }

    override fun getItemCount() = hoursData.size

    class HolderHour(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(hours: Hours) {
            val binding = HourItemBinding.bind(itemView)
            binding.hourTime.text = SimpleDateFormat("H:m").format(hours.hour)
            binding.hourWeather.text = hours.temp.toString()
            binding.hourIcon.setImageDrawable(
                ContextCompat.getDrawable(
                    itemView.context,
                    R.drawable.sun
                )
            )
        }
    }
}