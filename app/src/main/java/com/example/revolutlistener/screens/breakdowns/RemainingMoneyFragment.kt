package com.example.revolutlistener.screens.breakdowns

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.android.trackmysleepquality.database.AppDatabase
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

        binding = RemainingMoneyFragmentBinding.inflate(inflater)

        val application = requireNotNull(this.activity).application
        val dataSource = AppDatabase.getInstance(application).spendingDatabaseDao

        val viewModelFactory = SleepTrackerViewModelFactory(dataSource, application)

        binding.viewmodel = viewModel
        binding.lifecycleOwner = this

        return binding.root
    }
}