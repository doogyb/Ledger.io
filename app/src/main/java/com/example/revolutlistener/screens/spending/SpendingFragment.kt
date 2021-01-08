package com.example.revolutlistener.screens.spending

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.revolutlistener.R
import com.example.revolutlistener.databinding.SpendingFragmentBinding

class SpendingFragment : Fragment() {

    private lateinit var viewModel: SpendingViewModel
    private lateinit var binding: SpendingFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = SpendingFragmentBinding.inflate(inflater)
        return binding.root
    }

}