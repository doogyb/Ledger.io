package com.example.revolutlistener.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.revolutlistener.database.AppDatabase
import com.example.revolutlistener.database.Budget
import com.example.revolutlistener.database.Spend
import com.example.revolutlistener.domain.Amount
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

const val TAG = "LedgerRepository"

class LedgerRepository(private val database: AppDatabase) {

    val budgets: LiveData<List<Amount>> = database.ledger.getAllBudgets()

    fun handleSpend(amount: Amount) {
        GlobalScope.launch {

            Log.i(TAG, "handling Spend...")
            var id = database.amountDao.insert(amount)
            database.spendDao.insert(Spend(id))

            val currentBudget = budgets.value?.get(0) ?: Amount(0, 0)
            val newBudget = currentBudget - amount
            id = database.amountDao.insert(newBudget)
            database.budgetDao.insert(Budget(id))
            database.spendDao.insert(Spend(id))
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