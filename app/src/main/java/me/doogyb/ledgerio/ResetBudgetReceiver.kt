package me.doogyb.ledgerio

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.preference.PreferenceManager
import me.doogyb.ledgerio.database.AppDatabase
import me.doogyb.ledgerio.domain.Amount
import me.doogyb.ledgerio.repository.LedgerRepository

private const val TAG = "MyBroadcastReceiver"

class ResetBudgetReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val ledgerRepository = LedgerRepository(AppDatabase.getInstance(context))
        // Get copy of what the budget was initially set to
        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
        val budget = preferences.getFloat("budget", 0.0F)
        // Set new budget and clear the days spending
        ledgerRepository.setBudget(Amount(budget), context)
        ledgerRepository.clearTodaysSpend()
    }
}
