package com.example.revolutlistener.screens.budget

import android.app.Application
import androidx.lifecycle.*
import com.example.revolutlistener.database.LedgerDao

const val TAG = "RemainingMoneyViewModel"

class RemainingMoneyViewModel(
    ledger: LedgerDao,
    application: Application
) : AndroidViewModel(application) {

    private val currentBudget = ledger.getCurrentBudget()

    // TODO get current number of days in Month
    val dailyRemaining: LiveData<String> = Transformations.map(currentBudget) { budget ->
        if (budget != null) (budget / 30).toString() else "€0.00"
    }

    val monthlyRemaining: LiveData<String> = Transformations.map(currentBudget) { budget ->
        budget?.toString() ?: "€0.00"
    }
}