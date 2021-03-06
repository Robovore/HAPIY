package com.hapiy.ui.home

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.hapiy.MainActivity
import com.hapiy.R
import java.time.LocalDateTime


class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        val textView: TextView = root.findViewById(R.id.text_home)

        // Print Date
        homeViewModel.text.observe(viewLifecycleOwner, Observer {textView.text = it })

        // Print Scores
        val activity = this.activity as MainActivity?
        val sleepScoreText: TextView = root.findViewById(R.id.sleepScore)
        val sleepScore: String = activity?.getPillarValue(activity?.getDateAsInt(LocalDateTime.now()), MainActivity.PILLAR.SLEEP.ordinal, MainActivity.SLEEP_TYPE.SLEEP_SCORE.ordinal).toString()
        sleepScoreText.text = sleepScore

        val bodyScoreText: TextView = root.findViewById(R.id.fitnessScore)
        val bodyScore: String = activity?.getPillarValue(activity?.getDateAsInt(LocalDateTime.now()), MainActivity.PILLAR.BODY.ordinal, MainActivity.BODY_TYPE.FITNESS_SCORE.ordinal).toString()
        bodyScoreText.text = bodyScore

        val relaxScoreText: TextView = root.findViewById(R.id.relaxScore)
        val relaxScore: String = activity?.getPillarValue(activity?.getDateAsInt(LocalDateTime.now()), MainActivity.PILLAR.MIND.ordinal, MainActivity.MIND_TYPE.MIND_SCORE.ordinal).toString()
        relaxScoreText.text = relaxScore

        val createScoreText: TextView = root.findViewById(R.id.createScore)
        val createScore: String = activity?.getPillarValue(activity?.getDateAsInt(LocalDateTime.now()), MainActivity.PILLAR.CREATE.ordinal, MainActivity.CREATE_TYPE.CREATE_SCORE.ordinal).toString()
        createScoreText.text = createScore

        val conditionScoreText: TextView = root.findViewById(R.id.operationalScore)
        val conditionScore: String = ((sleepScore.toInt()+bodyScore.toInt()+relaxScore.toInt()+createScore.toInt())/4).toString()
        conditionScoreText.text = conditionScore



        return root
    }
}
