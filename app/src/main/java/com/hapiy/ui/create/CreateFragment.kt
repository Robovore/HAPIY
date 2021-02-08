package com.hapiy.ui.create

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

class CreateFragment : Fragment() {

    private lateinit var createViewModel: CreateViewModel

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        createViewModel =
                ViewModelProviders.of(this).get(CreateViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_create, container, false)
        val textView: TextView = root.findViewById(R.id.text_create)
        createViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })

        val activity = this.activity as MainActivity?
        val createScore: String = activity?.getPillarValue(getDateAsInt(LocalDateTime.now()), MainActivity.PILLAR.CREATE.ordinal, MainActivity.CREATE_TYPE.CREATE_SCORE.ordinal).toString()

        val scoreText: TextView = root.findViewById(R.id.create1Score)
        if( createScore != "0" )
        {
            scoreText.text = "$createScore%"
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
