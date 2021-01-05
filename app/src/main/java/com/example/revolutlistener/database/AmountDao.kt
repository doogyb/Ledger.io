package com.example.revolutlistener.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface AmountDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(amount: AmountTable): Long

    @Update
    suspend fun update(amount: AmountTable)

    @Query("SELECT euro_amount, cent_amount from amount_table WHERE id = :key")
    suspend fun getAmount(key: Long): Amount?

    @Query("DELETE from amount_table")
    suspend fun clear()

    @Query("SELECT euro_amount, cent_amount from amount_table ORDER BY id DESC LIMIT 1")
    fun getCurrentAmount(): LiveData<Amount>

    @Query("SELECT euro_amount, cent_amount FROM amount_table ORDER BY id DESC")
    fun getAmountHistory(): LiveData<List<Amount>>
}