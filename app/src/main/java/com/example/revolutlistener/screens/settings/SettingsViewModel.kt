package com.example.revolutlistener.screens.settings

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.example.revolutlistener.database.AppDatabase
import com.example.revolutlistener.database.LedgerDao
import com.example.revolutlistener.domain.Amount
import com.example.revolutlistener.repository.LedgerRepository

//const val TAG = "SettingsViewModel"

class SettingsViewModel(val ledger: LedgerDao, application: Application) : AndroidViewModel(application) {

    private val ledgerRepository = LedgerRepository(AppDatabase.getInstance(application))

    fun onSaveSettings(budgetAmount: Int, budgetInterval: Int) {
        // do some stuff?
        Log.i(TAG, "Amount: $budgetAmount, Interval: $budgetInterval")
//        ledger.in
        ledgerRepository.setBudget(Amount(budgetAmount, 0))

    }
}