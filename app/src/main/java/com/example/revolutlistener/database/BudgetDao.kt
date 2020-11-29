package com.example.revolutlistener.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface BudgetDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(budget: Budget)

    @Update
    suspend fun update(budget: Budget)

    @Query("SELECT * from budget_table WHERE budgetId = :key")
    suspend fun getBudget(key: Long): Budget?

    @Query("DELETE from budget_table")
    suspend fun clear()

    @Query("SELECT * from budget_table ORDER BY budgetId DESC LIMIT 1")
    suspend fun getCurrentBudget(): Budget?

    @Query("SELECT * FROM budget_table ORDER BY budgetId DESC")
    fun getBudgetHistory(): LiveData<List<Budget>>
}