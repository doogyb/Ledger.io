package com.example.revolutlistener.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.android.trackmysleepquality.database.Spend

@Dao
interface TotalDao {
    @Insert
    suspend fun insertTotal(total: Total)

    @Update
    suspend fun updateTotal(total: Total)

    @Query("SELECT * from totals_table WHERE totalId = :key")
    suspend fun getTotal(key: Long): Spend?

    @Query("DELETE from totals_table")
    suspend fun clear()

    @Query("SELECT * from totals_table ORDER BY totalId DESC LIMIT 1")
    suspend fun getLatestTotal(): Total?

    @Query("SELECT * FROM totals_table ORDER BY totalId DESC")
    fun getAllTotals(): LiveData<List<Total>>
}