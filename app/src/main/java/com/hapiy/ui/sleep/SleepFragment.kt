package com.hapiy.ui.sleep

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ScrollView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.hapiy.MainActivity
import com.hapiy.R
import de.ehsun.coloredtimebar.TimelineView
import java.text.SimpleDateFormat
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
//        var wakeList: Queue<Int> = activity?.wakeTimeDatabase

        val scoreText: TextView = root.findViewById(R.id.text_sleep)
        val sleepCyclesNotAvailable: TextView = root.findViewById(R.id.sleepCyclesNotAvailable)
        var sleepTimesList: List<TimelineView> = listOf(root.findViewById(R.id.sleepTimes1), root.findViewById(R.id.sleepTimes2), root.findViewById(R.id.sleepTimes3), root.findViewById(R.id.sleepTimes4), root.findViewById(R.id.sleepTimes5), root.findViewById(R.id.sleepTimes6), root.findViewById(R.id.sleepTimes7))
        if( sleepScore != "0" )
        {
            scoreText.text = "$sleepScore%"
            scoreText.textSize = 50F
            scoreText.extendedPaddingTop

            //hide add btn
            val addBtn: FloatingActionButton = root.findViewById(R.id.logSleepBtn)
            addBtn.hide()
        }

        if(activity?.wakeTimeDatabase?.size != 0)
        {
            sleepCyclesNotAvailable.visibility = View.INVISIBLE
            sleepTimesList.forEach{
                it.visibility = View.INVISIBLE
            }

            var i: Int = 0
            activity?.wakeTimeDatabase?.forEach {
                // set wake and sleep times into the time bands
                //timelineView.setAvailableTimeRange(listOf("07:00 - 10:15", "12:00 - 15:00"))
                val dateformat = SimpleDateFormat("hh:mm") // dateformat.format(c.getTime());
                var sleepTimeIt: Calendar = activity?.sleepTimeDatabase[i]
                // if asleep before midnight
                if (sleepTimeIt.timeInMillis > 43200000) {
                    sleepTimesList[i].setAvailableTimeRange(listOf("00:00 - ${dateformat.format(it.time)}", "${dateformat.format(sleepTimeIt.time)} - 24:59"))
                }
                else {
                    sleepTimesList[i].setAvailableTimeRange(listOf("${dateformat.format(sleepTimeIt.time)} - ${dateformat.format(it.time)}"))
                }
                sleepTimesList[i].visibility = View.VISIBLE
            }
        }
        else{
            sleepCyclesNotAvailable.visibility = View.VISIBLE
            sleepTimesList.forEach{
                it.visibility = View.INVISIBLE
            }
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
