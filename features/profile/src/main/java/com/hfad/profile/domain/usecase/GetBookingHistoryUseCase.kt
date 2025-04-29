package com.hfad.profile.domain.usecase

import com.hfad.profile.domain.repository.BookingHistoryRepository
import com.hfad.profile.history.HistoryItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetBookingHistoryUseCase @Inject constructor(
    private val repository: BookingHistoryRepository
) {
    operator fun invoke(): Flow<List<HistoryItem>> {
        return repository.getBookingHistory()
    }
}
