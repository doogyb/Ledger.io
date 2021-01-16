package com.example.revolutlistener.domain

import org.junit.Test

import org.junit.Assert.*

class AmountTest {

    @Test
    fun testEquals() {
    }

    @Test
    fun plus() {
        // Euro test
        assertEquals(Amount(5, 0), Amount(2, 0) + Amount(3, 0))
        // Cent test
        assertEquals(Amount(0, 90), Amount(0, 30) + Amount(0, 60))
        // Overflow test
        assertEquals(Amount(1, 0), Amount(0, 40) + Amount(0, 60))
        assertEquals(Amount(6, 10), Amount(2, 50) + Amount(3, 60))
    }

    @Test
    fun minus() {
        // Euro test
        assertEquals(Amount(3, 0), Amount(4, 0) - Amount(1, 0))
        // Cent test
        assertEquals(Amount(0, 30), Amount(0, 55) - Amount(0, 25))
        // Overflow tests
        assertEquals(Amount(0, 60), Amount(1, 0) - Amount(0, 40))
        assertEquals(Amount(3, 30), Amount(5, 10) - Amount(1, 80))
        // Negatives
        assertEquals(Amount(-1, 40), Amount(2, 10) - Amount(3, 50))
    }

    @Test
    fun div() {
        // Simple division
        assertEquals(Amount(5, 0), Amount(25, 0) / 5)
        // Recurring
        assertEquals(Amount(3, 33), Amount(10, 0) / 3)
        // Dividing by 31 days - rounding? Floats round up it seems
        assertEquals(Amount(16, 13), Amount(500, 0) / 31)
    }

    @Test
    fun parseString() {
        // Test with single digits
        assertEquals(Amount.parseString("5.0"), Amount(5, 0))
        // Test with multiple digits
        assertEquals(Amount.parseString("50.50"), Amount(50, 50))
        // Test with zero leading digits for cents
        assertEquals(Amount.parseString("5.05"), Amount(5, 5))
        // Test ignoring currency symbol
        assertEquals(Amount.parseString("€5.05"), Amount(5, 5))
        // Test when no cents or . is given in string
        assertEquals(Amount.parseString("500"), Amount(500, 0))
    }

    @Test
    fun testToString() {
        // Test without leading zeros
        assertEquals("€50.50", Amount(50, 50).toString())
        // Test with leading zeros
        assertEquals("€50.05", Amount(50, 5).toString())
        // Test round number - double zeros
        assertEquals("€50.00", Amount(50, 0).toString())
    }
    @Test
    fun sum() {
        val amounts: List<Amount> = (1..5).map {Amount(it, it*10)}
        assertEquals(Amount(16, 50), amounts.sumUp())
    }
    @Test
    fun fromFloat() {
        // Whole number
        assertEquals(Amount(10, 0), Amount(10.0F))
        // Floating point
        assertEquals(Amount(10, 52), Amount(10.52F))
        // Recurring
        assertEquals(Amount(10, 52), Amount(10.52222F))
    }
}