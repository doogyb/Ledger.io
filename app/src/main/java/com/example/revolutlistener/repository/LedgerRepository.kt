package com.example.revolutlistener.repository

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.preference.PreferenceManager
import com.example.revolutlistener.database.AppDatabase
import com.example.revolutlistener.database.Budget
import com.example.revolutlistener.database.Spend
import com.example.revolutlistener.domain.Amount
import com.example.revolutlistener.domain.sumUp
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import com.example.revolutlistener.notifications.createUpdatedBudgetNotification

const val TAG = "LedgerRepository"

class LedgerRepository(private val database: AppDatabase) {

    val budgets: LiveData<List<Amount>> = database.ledger.getAllBudgets()
    val spends: LiveData<List<Amount>> = database.ledger.getTodaysExpenditure()

    val spentToday : LiveData<Amount> = Transformations.map(spends) {
        it.sumUp()
    }

    fun handleSpend(context: Context, spendAmount: Amount) {
        val sharedPreferences : SharedPreferences =
            PreferenceManager.getDefaultSharedPreferences(context)

        val dailyLimit = Amount(sharedPreferences.getFloat("daily_limit", 0.0F))

        Log.d(TAG, "dailyLimit: $dailyLimit")

        GlobalScope.launch {

            Log.i(TAG, "handling Spend...")

            // inserting spend to amount and spend tables
            var id = database.amountDao.insert(spendAmount)
            database.spendDao.insert(Spend(id))

            // getting current value of budget synchronously
            val currentBudget = database.ledger.getCurrentBudget()
            // calculating new budget
            val newBudget = currentBudget - spendAmount

            Log.i(TAG, "curentBudget: $currentBudget, spend: $spendAmount, newBudget: $newBudget")

            // inserting new budget to amount and budget tables
            id = database.amountDao.insert(newBudget)
            database.budgetDao.insert(Budget(id))

            // retrieving todays spends synchronously
            val todaysSpends: List<Amount> = database.ledger.getTodaysExpenditureSync()
            val spentToday = todaysSpends.sumUp()


            val leftToSpend = dailyLimit - spentToday

            if (sharedPreferences.getBoolean("notifications_enabled", true)) {
                createUpdatedBudgetNotification(context, spentToday, leftToSpend)
            }

        }
    }

    /*
    TODO Attach observer to Spend table, use this to update Budget table async style
    then sent the notification update using createUpdatedBudgetNotification
    */

    fun setBudget(amount: Amount) {
        GlobalScope.launch {

            Log.i(TAG, "handling Budget set")
            var id = database.amountDao.insert(amount)
            database.budgetDao.insert(Budget(id))
        }
    }

    fun clearTodaysSpend() {
        GlobalScope.launch {
            database.ledger.clearToday()
        }
    }
}
