package com.example.revolutlistener.notifications

import com.example.revolutlistener.domain.Amount
import org.junit.Assert
import org.junit.Test
import org.junit.Assert.*

class NotificationUtilKtTest {

    @Test
    fun testIsMoneySpentText() {
        // euro
        assertTrue(isMoneySpentNotification("Paid €10.50"))
        // Loads of money
        assertTrue(isMoneySpentNotification("Paid €10000.50"))
//         Need to check this ->
        assertTrue(isMoneySpentNotification("Paid €10"))
        // And this, although presumably this case will be very rare
//        assertTrue(isMoneySpentNotification("Paid €100,00.50"))



    }

    @Test
    fun testParseMonetaryAmount() {
        // euro
        assertEquals(Amount(10, 50), parseMonetaryAmount("Paid €10.50"))
        // Loads of money
        assertEquals(Amount(10000, 50), parseMonetaryAmount("Paid €10000.50"))
        // Need to check this ->
        assertEquals(Amount(10, 0), parseMonetaryAmount("Paid €10"))
        // And this, although presumably this case will be very rare
//        assertEquals(Amount(10000, 50), parseMonetaryAmount("€100,00.50"))
    }
}