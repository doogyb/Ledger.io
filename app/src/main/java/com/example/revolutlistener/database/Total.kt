package com.example.revolutlistener.database

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "totals_table")
data class Total(
    @NonNull @PrimaryKey @ColumnInfo(name="total_key")
    var totalId: Long = 0L,
    @ColumnInfo(name = "euro_amount")
    var euroAmount: Int = 0,
    @ColumnInfo(name = "cent_amount")
    var centAmount: Int = 0
)