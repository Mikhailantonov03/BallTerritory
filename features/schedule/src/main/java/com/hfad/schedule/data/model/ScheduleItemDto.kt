package com.hfad.schedule.data.model

data class ScheduleItemDto(
    val id: String,
    val title: String,
    val trainer: String,
    val timestamp: Long,
    val durationMinutes: Int
)
