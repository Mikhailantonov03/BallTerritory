package com.hfad.database.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [BookingEntity::class], version = 1)
abstract class BookingDatabase : RoomDatabase() {
    abstract fun bookingDao(): BookingDao
}
