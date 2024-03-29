package com.example.weatherapp.room

import androidx.room.*

@Dao
interface HistoryDao {
    @Query("INSERT INTO HISTORY_TABLE (city, temperature, feelsLike, icon) VALUES(:city, :temperature, :feelsLike, :icon)")
    fun nativeInsert(city: String, temperature: Int, feelsLike: Int, icon: String)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(entity: HistoryEntity)

    @Delete
    fun delete(entity: HistoryEntity)

    @Query("Delete from HISTORY_TABLE")
    fun deleteAll()

    @Update
    fun update(entity: HistoryEntity)

    @Query("SELECT * FROM HISTORY_TABLE")
    fun getAll(): List<HistoryEntity>

    @Query("SELECT * FROM HISTORY_TABLE WHERE city=:city")
    fun getHistoryForCity(city: String): List<HistoryEntity>
}