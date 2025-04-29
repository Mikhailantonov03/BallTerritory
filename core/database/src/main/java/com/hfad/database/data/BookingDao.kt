package com.hfad.database.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow



@Dao
interface BookingDao {
    @Query("SELECT * FROM bookings")
    fun getAllBookings(): Flow<List<BookingEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBooking(booking: BookingEntity)

    @Delete
    suspend fun deleteBooking(booking: BookingEntity)

    @Query("DELETE FROM bookings WHERE scheduleItemId = :scheduleItemId")
    suspend fun deleteByScheduleItemId(scheduleItemId: String)

    @Query("SELECT EXISTS(SELECT 1 FROM bookings WHERE scheduleItemId = :scheduleItemId)")
    suspend fun isBooked(scheduleItemId: String): Boolean
}
