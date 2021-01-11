package com.example.revolutlistener

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast

private const val TAG = "MyBroadcastReceiver"

class ResetBudgetReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        Log.d(TAG, "Alarm Broadcast Received")
        Toast.makeText(context, "Alarm Broadcast Received", Toast.LENGTH_LONG).show()
    }
}
