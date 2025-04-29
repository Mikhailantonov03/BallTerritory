package com.hfad.schedule.data.mapper



 import com.hfad.database.data.BookingEntity
 import com.hfad.schedule.ui.model.Booking

fun BookingEntity.toDomain(): Booking = Booking(
    id = id,
    scheduleItemId = scheduleItemId,
    userId = userId,
    timestamp = timeStamp
)
