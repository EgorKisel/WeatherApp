package com.example.weatherapp.view.history

import android.view.Gravity
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.R
import com.example.weatherapp.databinding.HistoryItemBinding
import com.example.weatherapp.repository.Weather
import com.example.weatherapp.utils.addDegree
import kotlinx.android.synthetic.main.history_item.view.*

class HistoryAdapter : RecyclerView.Adapter<HistoryHolder>() {

    private var data: List<Weather> = arrayListOf()
    private lateinit var listener: ClickHistory

    fun setOnNoteClickListener(listener: ClickHistory) {
        this.listener = listener
    }

    fun setData(data: List<Weather>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryHolder {
        val binding = HistoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HistoryHolder(binding.root, this.listener)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: HistoryHolder, position: Int) {
        holder.bind(data[position])
    }
}

class HistoryHolder(view: View, private val listener: ClickHistory) : RecyclerView.ViewHolder(view),
    PopupMenu.OnMenuItemClickListener {

    private lateinit var weather: Weather
    private val popupMenu = PopupMenu(itemView.context, itemView, Gravity.RIGHT).also {
        it.inflate(R.menu.menu_history)
        it.setOnMenuItemClickListener(this)
    }

    fun bind(data: Weather) {
        this.weather = data
        if (layoutPosition != RecyclerView.NO_POSITION) {
            itemView.recyclerViewItem.text = String.format(
                "%s %s %s %8s",
                data.city.cityName,
                data.temperature.toString().addDegree(),
                data.feelsLike,
                data.icon
            )
            itemView.setOnClickListener {
                popupMenu.show()
            }
        }
    }

    override fun onMenuItemClick(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_history_del -> {
                listener.selectWeather(weather)
            }
            R.id.menu_history_del_all -> {
                listener.deleteHistory()
            }
        }
        return true
    }

}

interface ClickHistory {
    fun selectWeather(weather: Weather)
    fun deleteHistory()
}