package com.example.revolutlistener.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface SpendDao {

    @Insert
    suspend fun insert(spend: Spend)

    @Update
    suspend fun update(spend: Spend)

    @Query("SELECT * from spend_table WHERE spend_key = :key")
    suspend fun getSpend(key: Long): Spend?

    @Query("DELETE from spend_table")
    suspend fun clearSpend()

    @Query("SELECT * FROM spend_table ORDER BY spend_key DESC")
    fun getAllSpends(): LiveData<List<Spend>>


}
