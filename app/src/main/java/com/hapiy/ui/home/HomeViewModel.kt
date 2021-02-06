package com.hapiy.ui.home

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

class HomeViewModel : ViewModel() {

    @RequiresApi(Build.VERSION_CODES.O)
    private val _text = MutableLiveData<String>().apply {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            value = (LocalDateTime.now().format(
                DateTimeFormatter.ofLocalizedDate(
                    FormatStyle.LONG)))
        }
    }
    @RequiresApi(Build.VERSION_CODES.O)
    val text: LiveData<String> = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        _text
    } else {
        TODO("VERSION.SDK_INT < O")
    }


}