package me.doogyb.ledgerio.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


// Class then extended in domain.Models for addition, division functions etc.
@Entity(tableName = "amount_table")
open class AmountTable(
    @ColumnInfo(name = "euro_amount")
    val euro: Int,
    @ColumnInfo(name = "cent_amount")
    val cent: Int,
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    @ColumnInfo(name = "timestamp")
    var timestamp: Long = System.currentTimeMillis()
)

// Separate the types of amounts into Budget and Spend tables
@Entity(tableName = "budget_table")
data class Budget(
    @PrimaryKey(autoGenerate = true)
    var id: Long?
)

@Entity(tableName = "spend_table")
data class Spend(
    @PrimaryKey
    var id: Long? = 0L
)