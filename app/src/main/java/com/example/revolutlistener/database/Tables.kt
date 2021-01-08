package com.example.revolutlistener.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "amount_table")
open class AmountTable(
    @ColumnInfo(name = "euro_amount")
    val euro: Int,
    @ColumnInfo(name = "cent_amount")
    val cent: Int,
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
)

@Entity(tableName = "budget_table")
data class Budget(
    @PrimaryKey(autoGenerate = true)
    var id: Long?
)

@Entity(tableName = "spend_table")
data class Spend(
    @PrimaryKey @ColumnInfo(name="spend_key")
    var id: Long? = 0L
)