package com.example.revolutlistener.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query

@Dao
interface LedgerDao {

    @Query("SELECT * from amount_table ORDER BY id DESC LIMIT 1")
    fun getCurrentBudget(): LiveData<Amount?>
//
//    @Query("SELECT * from budget_table ORDER BY budgetId DESC LIMIT 1")
//    fun getCurrentBudget(): LiveData<Budget>
}