package com.hfad.schedule.data.repository

import com.hfad.schedule.data.api.ScheduleApi
import com.hfad.schedule.data.mapper.toDomain
import com.hfad.schedule.ui.model.ScheduleItem
import javax.inject.Inject
import com.hfad.schedule.domain.repository.ScheduleRepository


class ScheduleRepositoryImpl @Inject constructor(
    private val api: ScheduleApi
) : ScheduleRepository {

    override suspend fun getSchedule(): Result<List<ScheduleItem>> {
        return runCatching {
            api.getSchedule().map { it.toDomain() }
        }
    }
}
