package com.example.revolutlistener.screens.breakdowns

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.revolutlistener.database.BudgetDao
import com.example.revolutlistener.database.LedgerDao

class RemainingMoneyViewModelFactory(
    private val dataSource: LedgerDao,
    private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RemainingMoneyViewModel::class.java)) {
            return RemainingMoneyViewModel(dataSource, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}