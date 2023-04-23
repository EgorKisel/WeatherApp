package com.example.weatherapp.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.weatherapp.utils.HISTORY_TABLE

@Entity(tableName = HISTORY_TABLE)
data class HistoryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val city: String,
    val temperature: Int,
    val feelsLike: Int,
    val icon: String
)