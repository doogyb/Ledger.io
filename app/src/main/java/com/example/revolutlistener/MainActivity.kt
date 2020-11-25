package com.example.revolutlistener

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.content.ComponentName
import android.content.Context
import android.os.Build
import android.provider.Settings
import android.util.Log
import android.view.View
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.revolutlistener.util.NotificationService


private const val ENABLED_NOTIFICATION_LISTENERS = "enabled_notification_listeners"
private const val TAG = "mainActivity"
private const val CHANNEL_ID = "52"

class MainActivity : AppCompatActivity() {

    private var notificationsCreated = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Intent(this, NotificationService::class.java).also { intent ->
            startService(intent)
        }
        createNotificationChannel()
        Log.d(TAG, "is enabled: " + isNotificationServiceEnabled())

    }

    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.channel_name)
            val descriptionText = getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    fun postNotification(view: View) {
        var builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.notifcation_icon)
            .setContentTitle("My notification")
            .setContentText("Much longer text that cannot fit one line...")
            .setStyle(NotificationCompat.BigTextStyle()
                .bigText("Much longer text that cannot fit one line..."))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with(NotificationManagerCompat.from(this)) {
            // notificationId is a unique int for each notification that you must define
            notify(notificationsCreated, builder.build())
            notificationsCreated++
        }
    }

    private fun isNotificationServiceEnabled(): Boolean {
        val pkgName = packageName
        val flat = Settings.Secure.getString(
            contentResolver,
            ENABLED_NOTIFICATION_LISTENERS
        )
        if (!TextUtils.isEmpty(flat)) {
            val names = flat.split(":".toRegex()).dropLastWhile({ it.isEmpty() }).toTypedArray()
            for (i in names.indices) {
                val cn = ComponentName.unflattenFromString(names[i])
                if (cn != null) {
                    if (TextUtils.equals(pkgName, cn.packageName)) {
                        return true
                    }
                }
            }
        }
        return false
    }

}
