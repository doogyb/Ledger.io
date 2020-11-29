package com.example.revolutlistener.screens.breakdowns

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.revolutlistener.database.Total
import com.example.revolutlistener.database.TotalDao
import kotlinx.coroutines.launch

const val TAG = "RemainingMoneyViewModel"

class RemainingMoneyViewModel(
    private val database: TotalDao,
    application: Application
) : AndroidViewModel(application) {


    private val _dailyRemaining = MutableLiveData(0)
    private val _monthlyRemaining = MutableLiveData(0)

    private val currentTotal = MutableLiveData<Total>(Total(-1, 0, 0))

    val dailyRemaining: LiveData<Float> = Transformations.map(currentTotal) {
        it.euroAmount.toFloat() + it.centAmount / 10
    }
    val monthlyRemaining: LiveData<Int> = _monthlyRemaining


    init {
        initializeCurrentTotal()
    }

    private fun initializeCurrentTotal() {
        viewModelScope.launch {
            currentTotal.value = getCurrentTotalFromDatabase()
        }
    }

    private suspend fun getCurrentTotalFromDatabase(): Total {
        Log.i(TAG, "Running suspended function to get current Total")
        var curTotal = database.getCurrentTotal()
        if (curTotal == null) {
            curTotal = Total(-1, 0, 0)
        }
        return curTotal
    }
}