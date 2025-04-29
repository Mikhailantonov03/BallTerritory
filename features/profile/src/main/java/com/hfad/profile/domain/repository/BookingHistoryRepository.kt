package com.hfad.profile.domain.repository

import com.hfad.profile.history.HistoryItem
import kotlinx.coroutines.flow.Flow

interface BookingHistoryRepository {
    fun getBookingHistory(): Flow<List<HistoryItem>>
}
