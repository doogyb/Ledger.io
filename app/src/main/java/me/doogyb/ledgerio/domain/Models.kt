package me.doogyb.ledgerio.domain

import me.doogyb.ledgerio.database.AmountTable
import java.lang.Math.abs
import kotlin.math.roundToInt

fun Boolean.toInt() = if (this) 1 else 0

/**
 * Mostly self explanatory. Amount class mostly for budget and daily limit calculations. Useful
 * string methods in here too. I used the toFloat function for readability and ease of use. Not
 * very efficient but these methods are called very infrequently - every time a spend occurs.
 */

open class Amount(euro: Int, cent: Int, id: Long=0, timestamp: Long = System.currentTimeMillis()):
    AmountTable(euro, cent, id, timestamp) {

    override fun equals(other: Any?): Boolean = (other is Amount && euro == other.euro && cent == other.cent)

    operator fun plus(other: Amount) = Amount(this.toFloat() + other.toFloat())

    operator fun minus(other: Amount) = Amount(this.toFloat() - other.toFloat())

    operator fun div(denominator: Int): Amount = Amount(this.toFloat() / denominator)

    fun toFloat(): Float {
        return euro.toFloat() + cent.toFloat() / 100
    }

    fun toInt(): Int {
        return euro
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
            if (s.contains('.')) {
                val split = s.split(".")
                return Amount(Integer.parseInt(split[0]), Integer.parseInt(split[1]))
            }
            else {
                return Amount(Integer.parseInt(s), 0)
            }
        }
    }
}

// Sums a list of Amounts
fun List<Amount>.sumUp(): Amount {
    var idem = Amount(0, 0)
    this.forEach {
        idem += it
    }
    return idem
}

// Casts a float to Amount
fun Amount(floatAmount: Float): Amount {
    val euro = floatAmount.toInt()
    val cent = ((floatAmount - euro) * 100).roundToInt()
    return Amount(euro, abs(cent))
}