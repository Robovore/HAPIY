package com.hapiy.ui.mind

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
import com.hapiy.ui.create.CreateViewModel
import com.hapiy.ui.home.HomeFragment
import kotlinx.android.synthetic.main.fragment_sleep_scoring.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

//FROM MAIN ACTIVITY
//enum class CREATE_TYPE { CREATE_SCORE, ART, MUSIC, WORK, READ, COOK, CODE, DANCE }

class CreateScoringFragment : Fragment() {
    private lateinit var createViewModel: CreateViewModel

    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        createViewModel =
            ViewModelProviders.of(this).get(CreateViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_create_scoring, container, false)

        val createSeeker: SeekBar = root.findViewById(R.id.createSeeker)

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
