package com.hapiy.ui.home

import com.hapiy.ui.home.HomeViewModel


import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.hapiy.MainActivity
import com.hapiy.R
import com.hapiy.ui.home.HomeFragment
import kotlinx.android.synthetic.main.fragment_sleep_scoring.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

//FROM MAIN ACTIVITY
//enum class MIND_TYPE { MIND_SCORE, RELAX_SCORE, STRESS_SCORE }

class HappyScoringFragment : Fragment() {
    private lateinit var homeViewModel: HomeViewModel

    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_mood_scoring, container, false)

        val moodSeeker: SeekBar = root.findViewById(R.id.moodSeeker)

        return root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getDateAsInt(date: LocalDateTime? ): Int {
        if (date != null) {
            var dateCode = (date.format(DateTimeFormatter.ISO_ORDINAL_DATE)) //Year and day of year	'2012-337'

            val dateValues: List<String> = dateCode.split("-").map { it -> it.trim() }
            return (365 * (dateValues[0].toInt()-2021)) + dateValues[1].toInt()
        }
        return 0;
    }



}