package com.example.revolutlistener.screens.breakdowns

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android.trackmysleepquality.database.SpendingDatabaseDao

class RemainingMoneyViewModel(
    val database: SpendingDatabaseDao,
    application: Application
) : AndroidViewModel(application) {


    private val _dailyRemaining = MutableLiveData(0)
    private val _monthlyRemaining = MutableLiveData(0)

    val dailyRemaining: LiveData<Int> = _dailyRemaining
    val monthlyRemaining: LiveData<Int> = _monthlyRemaining

}