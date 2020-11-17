package com.hapiy.ui.sleep

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


class SleepFragment : Fragment() {

    private lateinit var sleepViewModel: SleepViewModel

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        sleepViewModel =
                ViewModelProviders.of(this).get(SleepViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_sleep, container, false)
//        val textView: TextView = root.findViewById(R.id.text_sleep)

        //----test---------
        val dateInt: Int = ((LocalDateTime.now()).format(DateTimeFormatter.BASIC_ISO_DATE)).toInt()
        //sleep = 0, consis = 0
        val activity = this.activity as MainActivity?
        val textTest: String = ((activity?.getPillarValue(0, 0, 0) ?: 100) + 1).toString()
        activity?.setPillarValue(0,0,0, (activity?.getPillarValue(0, 0, 0) ?: 100) + 1)
        val textView: TextView = root.findViewById(R.id.text_sleep)
        textView.text = textTest

        //---------------------
        sleepViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = textTest//it
        })

        return root
    }
}
