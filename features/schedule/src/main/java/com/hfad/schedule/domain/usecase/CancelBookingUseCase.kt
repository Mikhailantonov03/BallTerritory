package com.hfad.schedule.domain.usecase


import com.hfad.schedule.domain.repository.BookingRepository
import javax.inject.Inject

class CancelBookingUseCase @Inject constructor(
    private val bookingRepository: BookingRepository
) {
    suspend operator fun invoke(scheduleItemId: String): Result<Unit> {
        return bookingRepository.cancel(scheduleItemId)
    }
}
