package com.hfad.schedule.ui.mvi

import com.hfad.module.UiStatus
import com.hfad.schedule.ui.model.ScheduleItem

class ScheduleReducer {

    fun reduceScheduleLoaded(items: List<ScheduleItem>): ScheduleUiState {
        return ScheduleUiState(
            status = UiStatus.Idle,
            schedule = items,
            filteredSchedule = items
        )
    }

    fun reduceToLoading(previousState: ScheduleUiState): ScheduleUiState {
        return previousState.copy(status = UiStatus.Loading)
    }

    fun reduceError(message: String): ScheduleUiState =
        ScheduleUiState(status = UiStatus.Error(message))

    fun reduceSportFilter(state: ScheduleUiState, selectedSport: String?): ScheduleUiState {
        val filtered = if (selectedSport == null) {
            state.schedule
        } else {
            state.schedule.filter { it.title == selectedSport }
        }

        return state.copy(
            selectedSport = selectedSport,
            filteredSchedule = filtered
        )
    }
}
