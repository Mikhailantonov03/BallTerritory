package com.hfad.schedule.data.mapper

import com.hfad.schedule.data.model.ScheduleItemDto
import com.hfad.schedule.ui.model.ScheduleItem

fun ScheduleItemDto.toDomain(): ScheduleItem {
    return ScheduleItem(
        id = id,
        title = title,
        trainer = trainer,
        timestamp = timestamp,
        durationMinutes = durationMinutes
    )
}
