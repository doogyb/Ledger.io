package com.example.revolutlistener.screens.settings

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.example.revolutlistener.database.LedgerDao

//const val TAG = "SettingsViewModel"

class SettingsViewModel(val ledger: LedgerDao, application: Application) : AndroidViewModel(application) {
    // TODO: Implement the ViewModel

    fun onSaveSettings(budgetAmount: Int, budgetInterval: Int) {
        // do some stuff?
        Log.i(TAG, "Amount: $budgetAmount, Interval: $budgetInterval")
//        ledger.in

    }
}