package com.hfad.schedule.ui.mvi.Schedule

import com.hfad.schedule.ui.mvi.ScheduleCard.ScheduleCardIntent
import java.time.LocalDate

sealed interface ScheduleIntents {
    data class LoadScheduleForDate(val date: LocalDate) : ScheduleIntents
    data class OnSignUpClick(val scheduleId: String) : ScheduleIntents
    data class OnCancelClick(val scheduleId: String) : ScheduleIntents
    data class OnSportFilterSelected(val sport: String?) : ScheduleIntents
    data class OnDateSelected(val date: LocalDate) : ScheduleIntents

    data class OnScheduleCardIntent(
        val cardId: String,
        val intent: ScheduleCardIntent
    ) : ScheduleIntents
}
