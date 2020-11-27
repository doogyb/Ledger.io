package com.example.revolutlistener.screens.breakdowns

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.revolutlistener.R
import com.example.revolutlistener.databinding.RemainingMoneyFragmentBinding

class RemainingMoney : Fragment() {
    // Obtain ViewModel from ViewModelProviders
    private lateinit var viewModel: RemainingMoneyViewModel
    private lateinit var binding: RemainingMoneyFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate view and obtain an instance of the binding class
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.remaining_money_fragment,
            container,
            false
        )

        viewModel = ViewModelProvider(this).get(RemainingMoneyViewModel::class.java)

        binding.viewmodel = viewModel
        binding.lifecycleOwner = this

        return binding.root
    }
}