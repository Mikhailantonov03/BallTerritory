package com.hfad.schedule.domain.usecase

import com.hfad.schedule.domain.repository.BookingRepository
import com.hfad.schedule.ui.model.Booking
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetBookingsFlowUseCase @Inject constructor(
    private val bookingRepository: BookingRepository
) {
    operator fun invoke(): Flow<List<Booking>> {
        return bookingRepository.getBookingsFlow()
    }
}
