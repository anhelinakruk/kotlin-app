package com.example.dsw53518_kotlin.viewmodel

import android.content.Context
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dsw53518_kotlin.MainApplication
import com.example.dsw53518_kotlin.model.Reminder
import com.example.dsw53518_kotlin.utils.ReminderReceiver
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.Instant
import java.util.Calendar
import java.util.Date

class ReminderViewModel : ViewModel() {
    private val reminderDao = MainApplication.reminderDatabase.getReminderDao()

    val reminderList: LiveData<List<Reminder>> = reminderDao.getAllReminders()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            updatePassedReminders()
        }
    }

    private suspend fun updatePassedReminders() {
        val currentTime = Calendar.getInstance().apply {
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }.time
        val reminders = reminderDao.getAllRemindersSync()
        reminders.forEach { reminder ->
            if (reminder.reminderTime != null) {
                val reminderCalendar = Calendar.getInstance().apply {
                    time = reminder.reminderTime
                    set(Calendar.SECOND, 0)
                    set(Calendar.MILLISECOND, 0)
                }
                if (reminder.isActive && reminderCalendar.time.before(currentTime)) {
                    reminderDao.updateReminderActiveStatus(reminder.id, false)
                }
            }
        }
    }

    fun refreshReminders() {
        viewModelScope.launch(Dispatchers.IO) {
            updatePassedReminders()
        }
    }

    @RequiresApi(Build.VERSION_CODES.S)
    fun addReminderWithAlarm(context: Context, title: String, reminderTime: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            val calendar = Calendar.getInstance().apply {
                timeInMillis = reminderTime
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
            }
            val reminder = Reminder(
                title = title,
                createdAt = Date.from(Instant.now()),
                reminderTime = calendar.time,
                isActive = true
            )
            val id = reminderDao.addReminder(reminder).toInt()
            scheduleReminder(context, id, title, calendar.timeInMillis)
        }
    }

    @RequiresApi(Build.VERSION_CODES.S)
    private fun scheduleReminder(context: Context, reminderId: Int, title: String, timeInMillis: Long) {
        val intent = Intent(context, ReminderReceiver::class.java).apply {
            putExtra("title", title)
            putExtra("reminderId", reminderId)
            putExtra("reminderTime", timeInMillis)
        }
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            reminderId,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        if (alarmManager.canScheduleExactAlarms()) {
            val alarmInfo = AlarmManager.AlarmClockInfo(timeInMillis, pendingIntent)
            alarmManager.setAlarmClock(alarmInfo, pendingIntent)
        }
    }

    fun deleteReminder(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val alarmManager = MainApplication.instance.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val intent = Intent(MainApplication.instance, ReminderReceiver::class.java)
            val pendingIntent = PendingIntent.getBroadcast(
                MainApplication.instance,
                id,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
            alarmManager.cancel(pendingIntent)
            reminderDao.deleteReminder(id)
        }
    }
}
