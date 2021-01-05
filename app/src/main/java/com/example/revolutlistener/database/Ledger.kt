package com.example.revolutlistener.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "budget_table")
data class Budget(
    @PrimaryKey(autoGenerate = true)
    var budgetId: Long?
)

@Entity(tableName = "spend_table")
data class Spend(
    @PrimaryKey @ColumnInfo(name="spend_key")
    var spendId: Long? = 0L
)