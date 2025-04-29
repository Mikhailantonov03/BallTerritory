package com.hfad.schedule.domain.usecase

import com.hfad.schedule.ui.model.ScheduleItem
import com.hfad.schedule.ui.mvi.toLocalDate
import java.time.LocalDate
import javax.inject.Inject

class FilterScheduleUseCase @Inject constructor() {
    operator fun invoke(
        schedule: List<ScheduleItem>,
        selectedSport: String?,
        selectedDate: LocalDate
    ): List<ScheduleItem> {
        return schedule
            .filter { it.timestamp.toLocalDate() == selectedDate }
            .filter { selectedSport == null || it.title == selectedSport }
    }
}

