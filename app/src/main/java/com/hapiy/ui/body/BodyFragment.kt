package com.hapiy.ui.body

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.hapiy.MainActivity
import com.hapiy.R
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class BodyFragment : Fragment() {

    private lateinit var bodyViewModel: BodyViewModel

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        bodyViewModel =
                ViewModelProviders.of(this).get(BodyViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_body, container, false)
        val textView: TextView = root.findViewById(R.id.text_body)
        bodyViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })

        val activity = this.activity as MainActivity?
        val foodScore: String = activity?.getPillarValue(getDateAsInt(LocalDateTime.now()), MainActivity.PILLAR.BODY.ordinal, MainActivity.BODY_TYPE.FOOD_SCORE.ordinal).toString()
        val goodFoodScore: String = activity?.getPillarValue(getDateAsInt(LocalDateTime.now()), MainActivity.PILLAR.BODY.ordinal, MainActivity.BODY_TYPE.GOOD_FOOD.ordinal).toString()
        val badFoodScore: String = activity?.getPillarValue(getDateAsInt(LocalDateTime.now()), MainActivity.PILLAR.BODY.ordinal, MainActivity.BODY_TYPE.BAD_FOOD.ordinal).toString()
        val fitnessScore: String = activity?.getPillarValue(getDateAsInt(LocalDateTime.now()), MainActivity.PILLAR.BODY.ordinal, MainActivity.BODY_TYPE.FITNESS_SCORE.ordinal).toString()
        val lowFitnessScore: String = activity?.getPillarValue(getDateAsInt(LocalDateTime.now()), MainActivity.PILLAR.BODY.ordinal, MainActivity.BODY_TYPE.LOW_FITNESS.ordinal).toString()
        val highFitnessScore: String = activity?.getPillarValue(getDateAsInt(LocalDateTime.now()), MainActivity.PILLAR.BODY.ordinal, MainActivity.BODY_TYPE.HIGH_FITNESS.ordinal).toString()
        val conditionScore: String = ((fitnessScore.toInt()+foodScore.toInt())/2).toString()

        val conditionText: TextView = root.findViewById(R.id.bodyConditionScore)
        val fitnessText: TextView = root.findViewById(R.id.fitnessScore)
        val foodText: TextView = root.findViewById(R.id.foodScore)

        if( conditionScore != "0" )
        {
            conditionText.text = "$conditionScore%"
        }
        if( fitnessScore != "0" )
        {
            fitnessText.text = "$fitnessScore%"
        }
        if( foodScore != "0" )
        {
            foodText.text = "$foodScore%"
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
