package com.example.revolutlistener.domain

import com.example.revolutlistener.database.AmountTable

fun Boolean.toInt() = if (this) 1 else 0



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
        val centString = if (cent < 10) "0$cent" else cent.toString()
        return "€$euro.$centString"
    }

    override fun hashCode(): Int {
        var result = euro
        result = 31 * result + cent
        return result
    }

    companion object {
        /**
         * Ignores negative amounts
         */
        fun parseString(sAmount: String): Amount {
            // if string begins with currency symbol, ignore
            var s = sAmount
            if (listOf('$', '€').contains(sAmount[0])) {
                s = s.substring(1)
            }
            val split = s.split(".")
            return Amount(Integer.parseInt(split[0]), Integer.parseInt(split[1]))
        }
    }

}