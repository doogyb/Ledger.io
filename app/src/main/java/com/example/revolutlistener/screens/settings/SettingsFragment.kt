package com.example.revolutlistener.screens.settings

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.revolutlistener.R
import com.example.revolutlistener.database.AppDatabase
import com.example.revolutlistener.databinding.FragmentSettingsBinding

const val TAG = "SettingsFragment"

class SettingsFragment : Fragment() {

    private lateinit var viewModel: SettingsViewModel
    private lateinit var binding : FragmentSettingsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentSettingsBinding.inflate(inflater)

        val application = requireNotNull(this.activity).application
        val dataSource = AppDatabase.getInstance(application).ledger


        val viewModelFactory = SettingsViewModelFactory(dataSource, application)
        viewModel = ViewModelProvider(this, viewModelFactory).get(SettingsViewModel::class.java)
        binding.viewmodel = viewModel

        binding.saveSettings.setOnClickListener {

            val amountString = binding.budgetAmount.text.toString()
            val amount = Integer.parseInt(if (amountString == "") "0" else amountString)

            val intervalString = binding.budgetInterval.text.toString()
            val interval = Integer.parseInt(if (intervalString == "") "0" else intervalString)

            viewModel.onSaveSettings(amount, interval)
            findNavController().navigate(R.id.remainingMoney)
        }

        return binding.root
    }
}