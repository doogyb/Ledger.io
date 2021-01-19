package com.example.revolutlistener.screens

import android.os.Bundle
import android.text.Html
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.revolutlistener.R

class About : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.about_fragment, container, false)
        val aboutView = view.findViewById(R.id.about_view) as TextView

        aboutView.movementMethod = LinkMovementMethod.getInstance();

        return view
    }
}