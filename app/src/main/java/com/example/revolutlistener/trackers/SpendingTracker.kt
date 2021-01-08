package com.example.revolutlistener.trackers

import android.content.Context
import android.service.notification.StatusBarNotification
import android.util.Log
import com.example.revolutlistener.database.*
import com.example.revolutlistener.notifications.parseMonetaryAmount
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

const val TAG = "SpendingTracker"

fun handleSpend(context: Context, amount: Amount) {
    val db = AppDatabase.getInstance(context)
    GlobalScope.launch {

        Log.i(TAG, "handling Spend...")
        var id = db.amountDao.insert(amount)
        db.spendDao.insert(Spend(id))
        val currentBudget = db.ledger.getCurrentBudget().value ?: Amount(0, 0)
        val newBudget = currentBudget - amount
        id = db.amountDao.insert(newBudget)
        db.budgetDao.insert(Budget(id))
        db.spendDao.insert(Spend(id))
    }
}

