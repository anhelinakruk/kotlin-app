package com.example.dsw53518_kotlin.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.dsw53518_kotlin.model.Reminder

@Dao
interface ReminderDao {
    @Query("SELECT * FROM Reminder ORDER BY createdAt DESC")
    fun getAllReminders(): LiveData<List<Reminder>>

    @Query("SELECT * FROM Reminder ORDER BY createdAt DESC")
    suspend fun getAllRemindersSync(): List<Reminder>

    @Insert
    suspend fun addReminder(reminder: Reminder): Long

    @Query("DELETE FROM Reminder WHERE id = :id")
    suspend fun deleteReminder(id: Int)

    @Query("UPDATE Reminder SET isActive = :isActive WHERE id = :id")
    suspend fun updateReminderActiveStatus(id: Int, isActive: Boolean)
}
