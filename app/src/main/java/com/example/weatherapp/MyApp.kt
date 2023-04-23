package com.example.weatherapp

import android.app.Application
import androidx.room.Room
import com.example.weatherapp.room.HistoryDao
import com.example.weatherapp.room.MyDB

class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()
        appContext = this
    }

    companion object {
        private var db: MyDB? = null
        private var appContext: MyApp? = null
        fun getHistoryDao(): HistoryDao {
            if (db == null) {
                if (appContext != null) {
                    db = Room.databaseBuilder(appContext!!, MyDB::class.java, "test")
                        .build()
                } else {
                    throw java.lang.IllegalStateException("something went wrong and we have an empty appContext")
                }
            }
            return db!!.historyDao()
        }
    }
}