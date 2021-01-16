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

    @Query("SELECT amount_table.* FROM amount_table INNER JOIN budget_table ON budget_table.id == amount_table.id ORDER BY amount_table.id DESC")
    fun getAllBudgets(): LiveData<List<Amount>>

    // Needs to be synchronous in order to update the newest budget
    // Better to constantly keep track of latest budget in Repo?
    @Query("SELECT amount_table.* FROM amount_table INNER JOIN budget_table ON budget_table.id == amount_table.id ORDER BY amount_table.id DESC LIMIT 1")
    fun getCurrentBudget(): Amount

    @Query("SELECT amount_table.* FROM amount_table INNER JOIN spend_table ON spend_table.id == amount_table.id WHERE datetime(timestamp / 1000, 'unixepoch') > date('now')")
    fun getTodaysExpenditure(): LiveData<List<Amount>>

    @Query("SELECT amount_table.* FROM amount_table INNER JOIN spend_table ON spend_table.id == amount_table.id WHERE datetime(timestamp / 1000, 'unixepoch') > date('now')")
    fun getTodaysExpenditureSync(): List<Amount>

    @Query(
        "DELETE FROM spend_table\n" +
              "WHERE spend_table.id IN (\n" +
            "    SELECT  amount_table.id FROM amount_table \n" +
            "    INNER JOIN spend_table ON spend_table.id == amount_table.id \n" +
            "    WHERE datetime(timestamp / 1000, 'unixepoch') > date('now')\n" +
            ")")
    fun clearToday()

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
