package com.example.weatherapp.utils

import android.view.View
import com.google.android.material.snackbar.Snackbar

const val KEY_BUNDLE_WEATHER = "key"

class Utils {
}

fun View.showSnackBar(text: String, length: Int = Snackbar.LENGTH_LONG) {
    Snackbar.make(this, text, length).show()
}