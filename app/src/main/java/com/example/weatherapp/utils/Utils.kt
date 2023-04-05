package com.example.weatherapp.utils

import android.view.View
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.util.*

const val KEY_BUNDLE_WEATHER = "key"
const val FORMAT_DATE = "E, dd MMMM H:m"

class Utils {
}

fun View.showSnackBar(text: String, length: Int = Snackbar.LENGTH_LONG) {
    Snackbar.make(this, text, length).show()
}

fun Date.formatDate(): String = SimpleDateFormat(FORMAT_DATE, Locale.getDefault()).format(this)