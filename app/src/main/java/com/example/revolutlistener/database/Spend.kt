package com.example.revolutlistener.database

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "spend_table")
data class Spend(
        @NonNull @PrimaryKey @ColumnInfo(name="spend_key")
        var spendId: Long = 0L,
        @ColumnInfo(name = "euro_amount")
        var euroAmount: Int = 0,
        @ColumnInfo(name = "cent_amount")
        var centAmount: Int = 0
)

