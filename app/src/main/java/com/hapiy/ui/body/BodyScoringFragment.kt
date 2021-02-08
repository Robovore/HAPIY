package com.hapiy.ui.body

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
enum class BODY_TYPE { LOW_FITNESS, HIGH_FITNESS, GOOD_FOOD, BAD_FOOD, FITNESS_SCORE }

class BodyScoringFragment : Fragment() {
    private lateinit var bodyViewModel: BodyViewModel

    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bodyViewModel =
            ViewModelProviders.of(this).get(BodyViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_body_scoring, container, false)

        val lowFitness: SeekBar = root.findViewById(R.id.lowFitnessSeeker)
        val highFitness: SeekBar = root.findViewById(R.id.highFitnessSeeker)
        val goodFood: SeekBar = root.findViewById(R.id.goodFoodSeeker)
        val badFood: SeekBar = root.findViewById(R.id.badFoodSeeker)

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
