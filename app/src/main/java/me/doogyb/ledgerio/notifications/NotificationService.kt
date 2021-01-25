package me.doogyb.ledgerio.notifications

import android.content.Intent
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.util.Log
import me.doogyb.ledgerio.database.AppDatabase
import me.doogyb.ledgerio.repository.LedgerRepository
private const val TAG = "NotificationService"

class NotificationService : NotificationListenerService() {

    private lateinit var ledgerRepository: LedgerRepository

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        // LedgerRepository instance needed to update database when spend has occured
        ledgerRepository = LedgerRepository(AppDatabase.getInstance(this))
        return super.onStartCommand(intent, flags, startId)

    }

    override fun onListenerConnected() {
        Log.v(TAG, "Listener is connected!")
    }

    override fun onNotificationPosted(sbn: StatusBarNotification) {

        if (isMoneySpentNotification(sbn)) {
            ledgerRepository.handleSpend(this, parseMonetaryAmount(sbn))
        }
    }
}