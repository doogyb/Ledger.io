package com.example.revolutlistener

import com.example.revolutlistener.database.Amount
import org.junit.Test

import org.junit.Assert.*

class MoneyTests {
    @Test
    fun addition_isCorrect() {
        // Euro test
        assertEquals(Amount(5, 0), Amount(2, 0) + Amount(3, 0))
        // Cent test
        assertEquals(Amount(0, 90), Amount(0, 30) + Amount(0, 60))
        // Overflow test
        assertEquals(Amount(1, 0), Amount(0, 40) + Amount(0, 60))
        assertEquals(Amount(6, 10), Amount(2, 50) + Amount(3, 60))

    }
    @Test
    fun subtraction_isCorrect() {
        // Euro test
        assertEquals(Amount(3, 0), Amount(4, 0) - Amount(1, 0))
        // Cent test
        assertEquals(Amount(0, 30), Amount(0, 55) - Amount(0, 25))
        // Overflow tests
        assertEquals(Amount(0, 60), Amount(1, 0) - Amount(0, 40))
        assertEquals(Amount(3, 30), Amount(5, 10) - Amount(1, 80))

    }
    @Test
    fun division_isCorrect() {
        // Simple division
        assertEquals(Amount(5, 0), Amount(25, 0) / 5)
        // Recurring
        assertEquals(Amount(3, 33), Amount(10, 0) / 3)
        // Dividing by 31 days
        assertEquals(Amount(16, 12), Amount(500, 0) / 31)
    }
}
