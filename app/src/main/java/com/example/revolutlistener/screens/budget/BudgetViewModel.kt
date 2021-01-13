package com.example.revolutlistener.screens.budget

import com.example.revolutlistener.util.SharedPreferenceStringLiveData
import android.app.Application
import android.content.SharedPreferences
import androidx.lifecycle.*
import androidx.preference.PreferenceManager
import com.example.revolutlistener.database.AppDatabase
import com.example.revolutlistener.domain.Amount
import com.example.revolutlistener.repository.LedgerRepository

const val TAG = "BudgetViewModel"

class BudgetViewModel(
    application: Application
) : AndroidViewModel(application) {


    private val ledgerRepository = LedgerRepository(AppDatabase.getInstance(application))
    private val budgets = ledgerRepository.budgets

    private val sharedPreferences : SharedPreferences =
        PreferenceManager.getDefaultSharedPreferences(application.applicationContext)

    val budgetPreference = SharedPreferenceStringLiveData(sharedPreferences, "budget_preference", "0")

    val budgetTotal = Transformations.map(budgetPreference) {

    }


    private val intervalPreference = SharedPreferenceStringLiveData(sharedPreferences, "interval_preference", "1")

    // Parse intervalPreference to integer (EditTextPreference is stored as String)
    private val intervalInt : LiveData<Int> = Transformations.map(intervalPreference) {
        Integer.parseInt(it)
    }

    // First Budget given ascending unique IDs - this is the current state of the budget
    val currentBudget : LiveData<Amount> = Transformations.map(budgets) {
        if (it.isNotEmpty()) it[0] else Amount(0, 0)
    }


    // Variable to track amount you can spend in a day given an interval and budget
    private val dailySpend : LiveData<Amount> = Transformations.map(intervalInt) {
        currentBudget.value?.div(it)
    }

    // TODO get current number of days in Month
    // Amount you can spend in a day given Budget and Interval/Budget Period
    val dailyRemaining : LiveData<Amount> = Transformations.map(currentBudget) {
        it.div(intervalInt.value ?: 1)
    }
}
