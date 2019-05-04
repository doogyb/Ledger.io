package com.example.revolutlistener

import android.app.Notification
import android.content.Intent
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.util.Log
import android.text.TextUtils
import android.os.Parcel
import android.os.Parcelable
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


private const val TAG = "NotificationService"
private const val PAID = "Paid"

class NotificationService : NotificationListenerService() {


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        Log.v(TAG, "started succesfully")
        Log.v(TAG, "and again?")
//        val requestIntent = Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS")
//        requestIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//        startActivity(requestIntent)
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onNotificationPosted(sbn: StatusBarNotification) {
        Log.i(TAG, "Got something!\n\n\n")
        Log.i(TAG, sbn.toString())
        Log.i(TAG, sbn.notification.extras["android.text"].toString())
        var notificationText = sbn.notification.extras["android.text"]

    }

    fun isMoneySpentNotification(sbn: StatusBarNotification): Boolean {

        if (sbn.packageName != "com.revolut.revolut") return false
        val notificationTexts = getText(sbn.notification) ?: return false

        notificationTexts.forEach {
            val l = it.split(" ")
            val paidIndex = l.indexOf(PAID)
            if (paidIndex != -1) {
                return true
            }
        }
        return false
    }

    override fun onListenerConnected() {
        Log.v(TAG, "Listener is connected!")
        val acNot = activeNotifications
        activeNotifications.forEach {
            if (it.packageName == "com.revolut.revolut") {

                Log.v(TAG + " revextras", it.toString())
                Log.v(TAG + " revextras", it.notification.contentView.toString())
                Log.v(TAG + " revextras", it.notification.extras.toString())
                Log.v(TAG + " moneyShot: ", getText(it.notification).toString())
            }
            Log.v(TAG + " notif: ", it.toString())

        }
    }

    fun getText(notification: Notification): List<String>? {
        // We have to extract the information from the view
        var views = notification.contentView
        if (views == null) return null

        // Use reflection to examine the m_actions member of the given RemoteViews object.
        // It's not pretty, but it works.
        val text = ArrayList<String>()
        try {
            val field = views.javaClass.getDeclaredField("mActions")
            field.isAccessible = true

            val actions = field.get(views) as ArrayList<Parcelable>

            // Find the setText() and setTime() reflection actions
            for (p in actions) {
                val parcel = Parcel.obtain()
                p.writeToParcel(parcel, 0)
                parcel.setDataPosition(0)

                // The tag tells which type of action it is (2 is ReflectionAction, from the source)
                val tag = parcel.readInt()
                if (tag != 2) continue

                // View ID
                parcel.readInt()

                val methodName = parcel.readString()
                if (methodName == null)
                    continue
                else if (methodName == "setText") {
                    // Parameter type (10 = Character Sequence)
                    parcel.readInt()

                    // Store the actual string
                    val t = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel).toString().trim { it <= ' ' }
                    text.add(t)
                } else if (methodName == "setTime") {
                    // Parameter type (5 = Long)
                    parcel.readInt()

                    val t = SimpleDateFormat("h:mm a").format(Date(parcel.readLong()))
                    text.add(t)
                }// Save times. Comment this section out if the notification time isn't important
                // Save strings

                parcel.recycle()
            }
        } catch (e: Exception) {
            Log.e("NotificationClassifier", e.toString())
        }
        // It's not usually good style to do this, but then again, neither is the use of reflection...

        return text
    }
}