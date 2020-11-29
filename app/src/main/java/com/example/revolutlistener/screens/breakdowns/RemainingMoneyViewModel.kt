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

    private val currentTotal = MutableLiveData(Total(0, 0, 0))

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

    fun updateTotal() {

        viewModelScope.launch {

            val euro = (currentTotal.value?.euroAmount ?: 0) + 1
            val newTotal = Total(null, euro, 0)
            insertTotal(newTotal)
            currentTotal.value = getCurrentTotalFromDatabase()

        }

    }

    private suspend fun getCurrentTotalFromDatabase(): Total {
        var curTotal = database.getCurrentTotal()
        if (curTotal == null) {
            curTotal = Total(0, 0, 0)
        }
        return curTotal
    }

    private suspend fun insertTotal(total: Total) {
        database.insertTotal(total)
    }
}