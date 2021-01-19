package com.example.revolutlistener

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.preference.PreferenceManager
import com.example.revolutlistener.database.AppDatabase
import com.example.revolutlistener.domain.Amount
import com.example.revolutlistener.repository.LedgerRepository

private const val TAG = "MyBroadcastReceiver"

class ResetBudgetReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val ledgerRepository = LedgerRepository(AppDatabase.getInstance(context))
        // Get copy of what the budget was initially set to
        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
        val budget = preferences.getFloat("budget", 0.0F)
        // Set new budget and clear the days spending
        ledgerRepository.setBudget(Amount(budget), context)
        ledgerRepository.clearTodaysSpend()
    }
}
