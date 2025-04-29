package com.hfad.schedule.ui.model

data class Booking(
    val id: String,
    val scheduleItemId: String,
    val userId: String,
    val timestamp: Long
)
