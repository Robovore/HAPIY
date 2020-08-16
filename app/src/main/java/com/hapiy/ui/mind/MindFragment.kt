package com.hapiy.ui.mind

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.hapiy.R

class MindFragment : Fragment() {

    private lateinit var mindViewModel: MindViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        mindViewModel =
                ViewModelProviders.of(this).get(MindViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_mind, container, false)
        val textView: TextView = root.findViewById(R.id.text_mind)
        mindViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}
