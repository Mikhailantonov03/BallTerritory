package com.hfad.database.data

import androidx.room.Entity
import androidx.room.PrimaryKey



    @Entity(tableName = "bookings")
    data class BookingEntity(
        @PrimaryKey val id: String,
        val scheduleItemId: String,
        val userId: String,
        val timeStamp: Long
    )
