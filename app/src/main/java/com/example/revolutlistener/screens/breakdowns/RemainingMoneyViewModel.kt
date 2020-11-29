package com.example.revolutlistener.screens.breakdowns

import android.app.Application
import androidx.lifecycle.*
import com.example.revolutlistener.database.Budget
import com.example.revolutlistener.database.BudgetDao
import kotlinx.coroutines.launch

const val TAG = "RemainingMoneyViewModel"

class RemainingMoneyViewModel(
    private val database: BudgetDao,
    application: Application
) : AndroidViewModel(application) {


    private val _dailyRemaining = MutableLiveData(0)
    private val _monthlyRemaining = MutableLiveData(0)

    private val currentBudget = MutableLiveData(Budget(0, 0, 0))

    val dailyRemaining: LiveData<Float> = Transformations.map(currentBudget) {
        it.euroAmount.toFloat() + it.centAmount / 10
    }
    val monthlyRemaining: LiveData<Int> = _monthlyRemaining


    init {
        initializeCurrentBudget()
    }

    private fun initializeCurrentBudget() {
        viewModelScope.launch {
            currentBudget.value = getCurrentBudgetFromDatabase()
        }
    }

    fun updateBudget() {

        viewModelScope.launch {

            val euro = (currentBudget.value?.euroAmount ?: 0) + 1
            val newBudget = Budget(null, euro, 0)
            insertBudget(newBudget)
            currentBudget.value = getCurrentBudgetFromDatabase()

        }

    }

    private suspend fun getCurrentBudgetFromDatabase(): Budget {
        var curBudget = database.getCurrentBudget()
        if (curBudget == null) {
            curBudget = Budget(0, 0, 0)
        }
        return curBudget
    }

    private suspend fun insertBudget(budget: Budget) {
        database.insert(budget)
    }
}