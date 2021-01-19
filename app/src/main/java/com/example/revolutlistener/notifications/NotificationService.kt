package com.example.revolutlistener.notifications

import android.content.Intent
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.util.Log
import com.example.revolutlistener.database.AppDatabase
import com.example.revolutlistener.repository.LedgerRepository
private const val TAG = "NotificationService"

class NotificationService : NotificationListenerService() {

    private lateinit var ledgerRepository: LedgerRepository

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        Log.v(TAG, "Listener service started successfully")
        ledgerRepository = LedgerRepository(AppDatabase.getInstance(this))

        return super.onStartCommand(intent, flags, startId)
    }

    override fun onListenerConnected() {
        Log.v(TAG, "Listener is connected!")
    }

    override fun onNotificationPosted(sbn: StatusBarNotification) {

        if (isMoneySpentNotification(sbn)) {
            ledgerRepository.handleSpend(this, parseMonetaryAmount(sbn));
        }
    }
}