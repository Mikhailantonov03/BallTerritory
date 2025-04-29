package com.hfad.schedule.data.repository



import com.hfad.data.token.user.UserStorage
import com.hfad.database.data.BookingDao
import com.hfad.database.data.BookingEntity
import com.hfad.schedule.data.api.ScheduleApi
import com.hfad.schedule.data.mapper.toDomain
import com.hfad.schedule.domain.repository.BookingRepository
import com.hfad.schedule.ui.model.Booking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class BookingRepositoryImpl @Inject constructor(
    private val api: ScheduleApi,
    private val userStorage: UserStorage,
    private val bookingDao: BookingDao
) : BookingRepository {

    private val bookings = MutableStateFlow<List<Booking>>(emptyList())

    override suspend fun getBookings(): List<Booking> {
        return bookingDao.getAllBookings().first().map { it.toDomain() }
    }

    override fun getBookingsFlow(): Flow<List<Booking>> =
        bookingDao.getAllBookings().map { list -> list.map { it.toDomain() } }

    override suspend fun book(scheduleItemId: String): Result<Unit> {
        val user = userStorage.getUser() ?: return Result.failure(Exception("User not found"))
        return runCatching {
            api.bookClass(scheduleItemId)
            val entity = BookingEntity(
                id = scheduleItemId,
                scheduleItemId = scheduleItemId,
                userId = user.id,
                timeStamp = System.currentTimeMillis()
            )
            bookingDao.insertBooking(entity)
        }
    }

    override suspend fun cancel(scheduleItemId: String): Result<Unit> {
        return runCatching {
            api.cancelBooking(scheduleItemId)
            bookingDao.deleteByScheduleItemId(scheduleItemId)
        }
    }

    override suspend fun isBooked(scheduleItemId: String): Boolean {
        return bookingDao.isBooked(scheduleItemId)
    }
}

