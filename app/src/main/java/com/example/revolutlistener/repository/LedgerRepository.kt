package com.example.revolutlistener.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import com.example.revolutlistener.database.AppDatabase
import com.example.revolutlistener.database.Budget
import com.example.revolutlistener.database.Spend
import com.example.revolutlistener.domain.Amount
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import com.example.revolutlistener.notifications.createUpdatedBudgetNotification

const val TAG = "LedgerRepository"

class LedgerRepository(private val database: AppDatabase) {

    val budgets: LiveData<List<Amount>> = database.ledger.getAllBudgets()

    fun handleSpend(context: Context, amount: Amount) {
        Log.i(TAG, "budgets: $budgets")
        val topBudget = budgets.value?.get(0)
        Log.i(TAG, "budgets: $topBudget")
        GlobalScope.launch {

            Log.i(TAG, "handling Spend...")

            var id = database.amountDao.insert(amount)
            database.spendDao.insert(Spend(id))

            val currentBudget = database.ledger.getCurrentBudget()
            val newBudget = currentBudget - amount

            Log.i(TAG, "curentBudget: $currentBudget, spend: $amount, newBudget: $newBudget")

            id = database.amountDao.insert(newBudget)
            database.budgetDao.insert(Budget(id))
            database.spendDao.insert(Spend(id))

            // TODO Actual amount left to spend in the day is different to average
            createUpdatedBudgetNotification(context, newBudget / 30)

        }
    }

    fun setBudget(amount: Amount) {
        GlobalScope.launch {

            Log.i(TAG, "handling Budget set")
            var id = database.amountDao.insert(amount)
            database.budgetDao.insert(Budget(id))
        }
    }
}