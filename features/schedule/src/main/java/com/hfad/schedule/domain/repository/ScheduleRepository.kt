package com.hfad.schedule.domain.repository

import com.hfad.schedule.ui.model.ScheduleItem

interface ScheduleRepository {
    suspend fun getSchedule(): Result<List<ScheduleItem>>
}
