package com.example.revolutlistener.screens.budget

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.preference.PreferenceManager
import com.example.revolutlistener.R
import com.example.revolutlistener.databinding.BudgetFragmentBinding
import com.example.revolutlistener.domain.Amount
import com.example.revolutlistener.notifications.isNotificationServiceEnabled
import pl.pawelkleczkowski.customgauge.CustomGauge

class BudgetFragment : Fragment() {

    private lateinit var viewModel: BudgetViewModel
    private lateinit var binding: BudgetFragmentBinding
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate view and obtain an instance of the binding class
        binding = BudgetFragmentBinding.inflate(inflater)
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

        val application = requireNotNull(this.activity).application
        val viewModelFactory = BudgetViewModelFactory(application)
        viewModel = ViewModelProvider(this, viewModelFactory).get(BudgetViewModel::class.java)

        binding.viewmodel = viewModel
        binding.lifecycleOwner = this

        // Send user to listener settings, only if not already allowed
        binding.goToListenerSettings.setOnClickListener {
            Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS").also {
                it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(it)
            }
        }

        // I can't bind these in the xml for some reason
        val budget = sharedPreferences.getFloat("budget", 0.0F)
        // Get granular readings by removing floating point to convert to int
        binding.remainingBudgetGuage.endValue = (budget * 100).toInt()
        viewModel.dailyRemaining.observe(viewLifecycleOwner, {
            observeGauge(binding.remainingDailyGuage, it)
        })

        val dailyLimit = sharedPreferences.getFloat("daily_limit", 0.0F)
        binding.remainingDailyGuage.endValue = (dailyLimit * 100).toInt()
        viewModel.currentBudget.observe(viewLifecycleOwner, {
            observeGauge(binding.remainingBudgetGuage, it)
        })

        return binding.root
    }

    private fun observeGauge(gauge: CustomGauge, budget: Amount) {

        val amtInt = (budget.toFloat() * 100).toInt()
        Log.d(TAG, "amtInt = $amtInt")
        if (amtInt > 0) {
            Log.d(TAG, "Setting as accentCOlor")
            gauge.value = amtInt
            gauge.pointStartColor = R.color.accentColor
            gauge.pointEndColor = R.color.accentColor
        }
        else {
            Log.d(TAG, "Setting as grey")
            gauge.value = 0
            gauge.pointStartColor = R.color.darkGrey
            gauge.pointEndColor = R.color.darkGrey
        }
    }

    override fun onResume() {
        super.onResume()
        // Check listener settings onResume and set visibility accordingly
        // TODO LiveData for this param?
        binding.listenerNotSetView.visibility =
            if (context?.let { isNotificationServiceEnabled(it) } == true) View.GONE
            else View.VISIBLE
    }
}