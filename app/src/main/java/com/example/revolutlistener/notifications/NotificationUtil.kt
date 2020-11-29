package com.example.revolutlistener.notifications

import android.app.Notification
import android.os.Parcel
import android.os.Parcelable
import android.service.notification.StatusBarNotification
import android.text.TextUtils
import android.util.Log
import com.example.revolutlistener.database.Total
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.random.Random.Default.nextInt

private const val PAID = "Paid"

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

fun parseMonetaryAmount(sbn: StatusBarNotification): Total {
    // Placeholder for now
    return Total(null, nextInt(0, 1000), nextInt(0, 100))
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
                val t = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel).toString()
                    .trim { it <= ' ' }
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
