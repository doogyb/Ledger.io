package com.example.revolutlistener.screens.budget

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.revolutlistener.database.AppDatabase
import com.example.revolutlistener.databinding.BudgetFragmentBinding

class BudgetFragment : Fragment() {

    private lateinit var viewModel: BudgetViewModel
    private lateinit var binding: BudgetFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate view and obtain an instance of the binding class

        binding = BudgetFragmentBinding.inflate(inflater)

        val application = requireNotNull(this.activity).application
        val dataSource = AppDatabase.getInstance(application).ledger

        val viewModelFactory = BudgetViewModelFactory(application)
        viewModel = ViewModelProvider(this, viewModelFactory).get(BudgetViewModel::class.java)

        binding.viewmodel = viewModel
        binding.lifecycleOwner = this

        return binding.root
    }
}