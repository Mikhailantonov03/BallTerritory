package com.hfad.profile.data

import com.hfad.database.data.BookingDao
import com.hfad.profile.domain.repository.BookingHistoryRepository
import com.hfad.profile.history.HistoryItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class BookingHistoryRepositoryImpl @Inject constructor(
    private val bookingDao: BookingDao
) : BookingHistoryRepository {

    private val dateFormatter = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())

    override fun getBookingHistory(): Flow<List<HistoryItem>> {
        return bookingDao.getAllBookings().map { entities ->
            entities.map { entity ->
                HistoryItem(
                    id = entity.id,
                    title = "Запись на тренировку", // Можно будет потом получать название тренировки
                    date = dateFormatter.format(Date(entity.timeStamp)),
                    status = "Записан" // Пока хардкод, потом добавим статус динамически
                )
            }
        }
    }
}
