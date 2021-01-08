package com.example.revolutlistener.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.revolutlistener.domain.Amount

/**
 * Daos for the different database tables. The Ledger Dao performs the more complex Queries
 */
@Dao
interface LedgerDao {

    @Query("SELECT amount_table.euro_amount, amount_table.cent_amount, amount_table.id FROM amount_table INNER JOIN budget_table on budget_table.id == amount_table.id ORDER BY amount_table.id DESC")
    fun getAllBudgets(): LiveData<List<Amount>>
}

@Dao
interface BudgetDao {
    @Insert
    suspend fun insert(budget: Budget)
}

@Dao
interface SpendDao {
    @Insert
    suspend fun insert(spend: Spend)
}

@Dao
interface AmountDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(amount: AmountTable): Long
}
