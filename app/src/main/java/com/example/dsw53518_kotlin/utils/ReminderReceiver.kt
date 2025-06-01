package com.example.dsw53518_kotlin.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.dsw53518_kotlin.R

class ReminderReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val title = intent.getStringExtra("title") ?: "Reminder"
        val triggerTime = intent.getLongExtra("reminderTime", 0L)
        if (triggerTime > 0 && System.currentTimeMillis() < triggerTime) {
            return
        }

        val builder = NotificationCompat.Builder(context, "reminder_channel")
            .setSmallIcon(R.drawable.reminder_icon)
            .setContentTitle("Reminder")
            .setContentText(title)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)

        with(NotificationManagerCompat.from(context)) {
            notify(System.currentTimeMillis().toInt(), builder.build())
        }
    }
}
