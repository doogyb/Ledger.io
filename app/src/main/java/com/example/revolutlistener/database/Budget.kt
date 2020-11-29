package com.example.revolutlistener.database

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "budget_table")
data class Budget(
    @PrimaryKey(autoGenerate = true)
    var budgetId: Long?,
    @ColumnInfo(name = "euro_amount")
    var euroAmount: Int = 0,
    @ColumnInfo(name = "cent_amount")
    var centAmount: Int = 0
)