package com.hfad.schedule.ui.mvi

import java.time.LocalDate

sealed interface ScheduleIntents {
    data class LoadScheduleForDate(val date: LocalDate) : ScheduleIntents
    data class OnSignUpClick(val scheduleId: String) : ScheduleIntents
    data class OnCancelClick(val scheduleId: String) : ScheduleIntents
    data class OnSportFilterSelected(val sport: String?) : ScheduleIntents
    data class OnDateSelected(val date: LocalDate) : ScheduleIntents


}
