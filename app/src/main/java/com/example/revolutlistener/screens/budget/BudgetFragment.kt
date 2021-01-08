package com.example.revolutlistener.screens.budget

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.revolutlistener.database.AppDatabase
import com.example.revolutlistener.databinding.FragmentBreakdownBinding

class RemainingMoney : Fragment() {

    private lateinit var viewModel: RemainingMoneyViewModel
    private lateinit var binding: FragmentBreakdownBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate view and obtain an instance of the binding class

        binding = FragmentBreakdownBinding.inflate(inflater)

        val application = requireNotNull(this.activity).application
        val dataSource = AppDatabase.getInstance(application).ledger

        val viewModelFactory = BudgetViewModelFactory(dataSource, application)
        viewModel = ViewModelProvider(this, viewModelFactory).get(RemainingMoneyViewModel::class.java)

        binding.viewmodel = viewModel
        binding.lifecycleOwner = this

        return binding.root
    }
}