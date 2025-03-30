package com.example.dsw53518_kotlin.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.dsw53518_kotlin.model.Reminder

@Dao
interface ReminderDao {
    @Query("SELECT * FROM Reminder ORDER BY createdAt DESC")
    fun getAllReminders(): LiveData<List<Reminder>>

    @Insert
    fun addReminder(reminder: Reminder)

    @Query("DELETE FROM Reminder WHERE id = :id")
    fun deleteReminder(id: Int)
}
