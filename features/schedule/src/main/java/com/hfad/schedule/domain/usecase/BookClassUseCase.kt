package com.hfad.schedule.domain.usecase

import com.hfad.schedule.domain.repository.BookingRepository
import javax.inject.Inject

class BookClassUseCase @Inject constructor(
    private val bookingRepository: BookingRepository
) {
    suspend operator fun invoke(scheduleItemId: String): Result<Unit> {
        return bookingRepository.book(scheduleItemId)
    }
}
