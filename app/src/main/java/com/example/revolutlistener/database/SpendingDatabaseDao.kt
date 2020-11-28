/*
 * Copyright 2019, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.trackmysleepquality.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.revolutlistener.database.Total

@Dao
interface SpendingDatabaseDao {

//    Spend Table Queries
    @Insert
    suspend fun insertSpend(spend: Spend)

    @Update
    suspend fun updateSpend(spend: Spend)

    @Query("SELECT * from spend_table WHERE spendId = :key")
    suspend fun getSpend(key: Long): Spend?

    @Query("DELETE from spend_table")
    suspend fun clearSpend()

    @Query("SELECT * FROM spend_table ORDER BY spendId DESC")
    fun getAllSpends(): LiveData<List<Spend>>

//    Totals Table Queries
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
