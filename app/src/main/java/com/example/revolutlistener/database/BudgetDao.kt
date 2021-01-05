package com.example.revolutlistener.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*

@Dao
interface BudgetDao {
    @Insert
    suspend fun insert(budget: Budget)

    @Update
    suspend fun update(budget: Budget)

    @Query("SELECT * from budget_table WHERE budgetId = :key")
    suspend fun get(key: Long): Budget?

    @Query("DELETE from budget_table")
    suspend fun clear()

    @Query("SELECT * from budget_table ORDER BY budgetId DESC LIMIT 1")
    fun getCurrentBudget(): LiveData<Budget>

    @Query("SELECT * FROM budget_table ORDER BY budgetId DESC")
    fun getBudgetHistory(): LiveData<List<Budget>>
}