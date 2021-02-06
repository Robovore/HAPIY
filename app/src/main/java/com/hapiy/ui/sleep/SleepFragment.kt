package com.hapiy.ui.sleep

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.hapiy.MainActivity
import com.hapiy.R
import com.hapiy.ui.home.HomeFragment
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

//FROM MAIN ACTIVITY
//enum class SLEEP_TYPE { SLEEP_TIME_AVG, WAKE_TIME_AVG, SLEEP_SCORE }
//val sleepTimeDatabase: Queue<Int> = LinkedList<Int>()
//val wakeTimeDatabase: Queue<Int> = LinkedList<Int>()

class SleepFragment : Fragment() {

    private lateinit var sleepViewModel: SleepViewModel

    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        sleepViewModel =
                ViewModelProviders.of(this).get(SleepViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_sleep, container, false)

        val activity = this.activity as MainActivity?
        val sleepScore: String = activity?.getPillarValue(getDateAsInt(LocalDateTime.now()), MainActivity.PILLAR.SLEEP.ordinal, MainActivity.SLEEP_TYPE.SLEEP_SCORE.ordinal).toString()

        val scoreText: TextView = root.findViewById(R.id.text_sleep)
        if( sleepScore != "0" )
        {
            scoreText.text = "$sleepScore%"
        }



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
