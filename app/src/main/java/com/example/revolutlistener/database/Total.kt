package com.example.revolutlistener.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "totals_table")
data class Total(
    @PrimaryKey(autoGenerate = true)
    var totalId: Long = 0L,
    @ColumnInfo(name = "euro_amount")
    var euroAmount: Int = 0,
    @ColumnInfo(name = "cent_amount")
    var centAmount: Int = 0
)