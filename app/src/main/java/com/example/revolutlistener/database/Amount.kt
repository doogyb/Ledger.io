package com.example.revolutlistener.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

fun Boolean.toInt() = if (this) 1 else 0

open class Amount(
    @ColumnInfo(name = "euro_amount")
    var euro: Int,
    @ColumnInfo(name = "cent_amount")
    var cent: Int) {

    override fun equals(other: Any?): Boolean = (other is Amount && euro == other.euro && cent == other.cent)

    operator fun plus(other: Amount): Amount {
        val totalCents = cent + other.cent
        return Amount(euro + other.euro + totalCents / 100, totalCents % 100)
    }

    operator fun minus(other: Amount): Amount {

        val totalCents = cent - other.cent
        val resultingEuros = euro - (other.euro + (totalCents < 0).toInt())
        val resultingCents = if (totalCents < 0) 100 + totalCents else totalCents
        return Amount(resultingEuros, resultingCents)
    }

    override fun toString(): String {
        return "€$euro.$cent"
    }

}

@Entity(tableName = "amount_table")
class AmountTable(
    euro: Int = 0,
    cent: Int = 0,
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
) : Amount(
    euro,
    cent
)