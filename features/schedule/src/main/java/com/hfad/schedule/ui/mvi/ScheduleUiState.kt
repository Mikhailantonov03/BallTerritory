package com.hfad.schedule.ui.mvi

import android.annotation.SuppressLint
import com.hfad.module.UiStatus
import com.hfad.schedule.ui.model.ScheduleItem
import java.time.LocalDate

data class ScheduleUiState(
    val status: UiStatus = UiStatus.Idle,
    val schedule: List<ScheduleItem> = emptyList(),
    val selectedSport: String? = null,
    val filteredSchedule: List<ScheduleItem> = emptyList(),
    @SuppressLint("NewApi") val selectedDate: LocalDate = LocalDate.now()
)
