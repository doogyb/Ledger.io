package com.example.revolutlistener.screens.breakdowns

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RemainingMoneyViewModel : ViewModel() {

    private val _dailyRemaining = MutableLiveData(0)
    private val _monthlyRemaining = MutableLiveData(0)

    val dailyRemaining: LiveData<Int> = _dailyRemaining
    val monthlyRemaining: LiveData<Int> = _monthlyRemaining

}