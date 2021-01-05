package com.example.revolutlistener.screens.breakdowns

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.revolutlistener.database.LedgerDao
import kotlinx.coroutines.launch

const val TAG = "RemainingMoneyViewModel"

class RemainingMoneyViewModel(
    ledger: LedgerDao,
    application: Application
) : AndroidViewModel(application) {


    private val _dailyRemaining = MutableLiveData(0)
    private val _monthlyRemaining = MutableLiveData(0)

    private val currentBudget = ledger.getCurrentBudget()

    val dailyRemaining: LiveData<String> = Transformations.map(currentBudget) { budget ->
        budget?.toString() ?: "€0.00"
    }
    val monthlyRemaining: LiveData<Int> = _monthlyRemaining

//    init {
//        initializeCurrentBudget()
//    }
//
//
//    private fun initializeCurrentBudget(): LiveData<Amount?> {
//        val budget = ledger.getCurrentBudget()
//        Log.i(TAG, budget.toString())
//        if (budget.value == null) {
//            Log.i(TAG, "Budget Value from getCurrentBudget is null...")
//            return MutableLiveData(Amount(0, 0))
//        }
//        return budget
//    }
}