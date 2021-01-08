package com.example.revolutlistener.screens.budget

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.revolutlistener.database.AppDatabase
import com.example.revolutlistener.database.LedgerDao
import com.example.revolutlistener.repository.LedgerRepository

const val TAG = "BudgetViewModel"

class BudgetViewModel(
    application: Application
) : AndroidViewModel(application) {


    private val ledgerRepository = LedgerRepository(AppDatabase.getInstance(application))
    private val budgets = ledgerRepository.budgets

    private val currentBudget = Transformations.map(budgets) {
        Log.i(TAG, it[0].toString())
        if (it.isNotEmpty()) it[0] else null
    }

    // TODO get current number of days in Month
    val dailyRemaining: LiveData<String> = Transformations.map(currentBudget) {
        if (it != null) (it / 30).toString() else "€0.00"
    }

    val monthlyRemaining: LiveData<String> = Transformations.map(currentBudget) { budget ->
        budget?.toString() ?: "€0.00"
    }
}