package com.hfad.schedule.domain.repository

import com.hfad.schedule.ui.model.Booking
import kotlinx.coroutines.flow.Flow

interface BookingRepository {
    suspend fun book(scheduleItemId: String): Result<Unit>
    suspend fun cancel(scheduleItemId: String): Result<Unit>
    suspend fun isBooked(scheduleItemId: String): Boolean

    suspend fun getBookings(): List<Booking>
    fun getBookingsFlow(): Flow<List<Booking>>
}

