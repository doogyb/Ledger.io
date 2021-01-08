package com.example.revolutlistener.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

fun Boolean.toInt() = if (this) 1 else 0

@Entity(tableName = "amount_table")
open class AmountTable(
    @ColumnInfo(name = "euro_amount")
    val euro: Int,
    @ColumnInfo(name = "cent_amount")
    val cent: Int,
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
)

open class Amount(euro: Int, cent: Int, id: Long=0) : AmountTable(euro, cent, id) {

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

    operator fun div(days: Int): Amount {
        val total = euro + cent / 100
        val divided: Double = total.toDouble() / days.toDouble()
        val euros = divided.toInt()
        val cents = ((divided - euros) * 100).toInt()
        return Amount(euros, cents)
    }

    override fun toString(): String {
        return "€$euro.$cent"
    }

    override fun hashCode(): Int {
        var result = euro
        result = 31 * result + cent
        return result
    }

}
