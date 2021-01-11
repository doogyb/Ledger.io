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

        // Should only be called if app has not been given valid permissions.
        Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS").also {
            it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(it)
        }

        return super.onStartCommand(intent, flags, startId)
    }

    override fun onListenerConnected() {
        Log.v(TAG, "Listener is connected!")
    }

    override fun onNotificationPosted(sbn: StatusBarNotification) {
        var notificationText = sbn.notification.extras["android.text"].toString()
        Log.i(TAG, notificationText)
        if (isMoneySpentNotification(notificationText)) {
            ledgerRepository.handleSpend(parseMonetaryAmount(sbn));
        }

    }
}