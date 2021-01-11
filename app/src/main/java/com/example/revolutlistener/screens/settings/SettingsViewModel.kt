package com.example.revolutlistener.screens.settings

import android.app.AlarmManager
import android.app.Application
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.SystemClock
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.example.revolutlistener.ResetBudgetReceiver
import com.example.revolutlistener.database.AppDatabase
import com.example.revolutlistener.database.LedgerDao
import com.example.revolutlistener.domain.Amount
import com.example.revolutlistener.repository.LedgerRepository

//const val TAG = "SettingsViewModel"

class SettingsViewModel(application: Application) : AndroidViewModel(application) {

    private val ledgerRepository = LedgerRepository(AppDatabase.getInstance(application))
    private val context = application.applicationContext

    private var alarmMgr: AlarmManager? = null
    private lateinit var alarmIntent: PendingIntent

    fun onSaveSettings(budgetAmount: Int, budgetInterval: Int) {

        ledgerRepository.setBudget(Amount(budgetAmount, 0))

        alarmMgr = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmIntent = Intent(context, ResetBudgetReceiver::class.java).let { intent ->
            PendingIntent.getBroadcast(context, 0, intent, 0)
        }

        alarmMgr?.setInexactRepeating(
            AlarmManager.RTC_WAKEUP,
            System.currentTimeMillis(),
            1000 * 10,
            alarmIntent
        )

    }
}