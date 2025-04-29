package com.hfad.schedule.ui.model

data class ScheduleItem(
    val id: String,
    val title: String,
    val trainer: String,
    val timestamp: Long,
    val durationMinutes: Int
)
