package com.example.revolutlistener.notifications

import android.content.Intent
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.example.revolutlistener.database.AppDatabase
import com.example.revolutlistener.screens.breakdowns.RemainingMoneyViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


private const val TAG = "NotificationService"

class NotificationService : NotificationListenerService() {

    private lateinit var remainingMoneyViewModel: RemainingMoneyViewModel

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        Log.v(TAG, "Listener service started successfully")

        val requestIntent = Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS")
        requestIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(requestIntent)

        return super.onStartCommand(intent, flags, startId)
    }

    override fun onListenerConnected() {
        Log.v(TAG, "Listener is connected!")
        // not entirely sure I'll need the below
//        val acNot = activeNotifications
//        activeNotifications.forEach {
//            if (it.packageName == "com.revolut.revolut") {
//
//                Log.v(TAG + " revextras", it.toString())
//                Log.v(TAG + " revextras", it.notification.contentView.toString())
//                Log.v(TAG + " revextras", it.notification.extras.toString())
//                Log.v(TAG + " moneyShot: ", getText(it.notification).toString())
//            }
//            Log.v(TAG + " notif: ", it.toString())
//
//        }
    }

    override fun onNotificationPosted(sbn: StatusBarNotification) {
        var notificationText = sbn.notification.extras["android.text"].toString()
        Log.i(TAG, notificationText)
        val budget = AppDatabase.getInstance(application).budgetDao
        GlobalScope.launch {
            budget.insert(parseMonetaryAmount(sbn))
        }

    }
}