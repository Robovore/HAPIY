package com.hapiy.ui.sleep

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.hapiy.MainActivity
import com.hapiy.R

class SleepFragment : Fragment() {

    private lateinit var sleepViewModel: SleepViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        sleepViewModel =
                ViewModelProviders.of(this).get(SleepViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_sleep, container, false)
        val textView: TextView = root.findViewById(R.id.text_sleep)
        sleepViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })

        return root
    }
}
