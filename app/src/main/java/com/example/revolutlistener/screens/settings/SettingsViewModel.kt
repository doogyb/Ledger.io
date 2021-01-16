package com.example.revolutlistener.screens.settings

import android.app.AlarmManager
import android.app.Application
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.lifecycle.AndroidViewModel
import androidx.preference.PreferenceManager
import com.example.revolutlistener.ResetBudgetReceiver
import com.example.revolutlistener.database.AppDatabase
import com.example.revolutlistener.domain.Amount
import com.example.revolutlistener.repository.LedgerRepository

//const val TAG = "SettingsViewModel"

class SettingsViewModel(application: Application) : AndroidViewModel(application) {

    private val ledger = LedgerRepository(AppDatabase.getInstance(application))
    private val context = application.applicationContext

    private val sharedPreferences : SharedPreferences =
        PreferenceManager.getDefaultSharedPreferences(context)
    private val editor = sharedPreferences.edit()

    private var alarmMgr: AlarmManager? = null
    private lateinit var alarmIntent: PendingIntent


    fun onSaveBudget(budget: Int) {
        ledger.setBudget(Amount(budget, 0))
        saveDailyLimit(budget, sharedPreferences.getString("interval", "1") ?: "1")
    }

    fun onSaveInterval(interval: Int) {
        // Set alarm to reset the Budget every interval days
        setAlarm(interval)
        saveDailyLimit(sharedPreferences.getString("budget", "0") ?: "0", interval)
    }

    // Function gets called when Budget and Interval are changed, i.e.
    // When onSaveBudget and onSaveInterval are called.
    private fun saveDailyLimit(budget: Int, interval: Int) {
        editor.putFloat("daily_limit", budget.toFloat() / interval)
    }
    private fun saveDailyLimit(budget: String, interval: Int) {
        saveDailyLimit(Integer.parseInt(budget), interval)
    }
    private fun saveDailyLimit(budget: Int, interval: String) {
        saveDailyLimit(budget, Integer.parseInt(interval))
    }

    private fun setAlarm(intervalAmount: Int) {
        alarmMgr = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmIntent = Intent(context, ResetBudgetReceiver::class.java).let { intent ->
            PendingIntent.getBroadcast(context, 0, intent, 0)
        }

        // TODO use budgetInterval - in particular implement calendar picker
        alarmMgr?.setInexactRepeating(
            AlarmManager.RTC_WAKEUP,
            System.currentTimeMillis(),
            AlarmManager.INTERVAL_DAY * intervalAmount,
            alarmIntent
        )
    }
}