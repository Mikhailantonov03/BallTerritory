package com.hfad.schedule.ui.mvi.Schedule

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
 import com.hfad.module.UiStatus
import com.hfad.schedule.ui.model.ScheduleItem
import com.hfad.schedule.ui.mvi.ScheduleCard.ScheduleCardIntent
import com.hfad.schedule.ui.mvi.ScheduleCard.ScheduleCardUiState
import com.hfad.schedule.ui.mvi.ScheduleCard.reduceCardUiState
import java.time.LocalDate

class ScheduleReducer {

    fun reduceScheduleLoaded(
        previousState: ScheduleUiState,
        items: List<ScheduleItem>,
        date: LocalDate
    ): ScheduleUiState {
        return previousState.copy(
            status = UiStatus.Idle,
            selectedDate = date,
            schedule = items,
            filteredSchedule = items,
        )
    }

    fun reduceToLoading(previousState: ScheduleUiState): ScheduleUiState {
        return previousState.copy(status = UiStatus.Loading)
    }

    fun reduceError(message: String): ScheduleUiState =
        ScheduleUiState(status = UiStatus.Error(message))

    @RequiresApi(Build.VERSION_CODES.O)
    fun reduceSportFilter(
        previousState: ScheduleUiState,
        schedule: List<ScheduleItem>,
        selectedSport: String?,
        selectedDate: LocalDate
    ): ScheduleUiState {
        val filtered = schedule
            .filter { it.timestamp.toLocalDate() == selectedDate }
            .filter {
                selectedSport == null ||
                        it.title.trim().equals(selectedSport.trim(), ignoreCase = true)
            }

        Log.d(
            "ScheduleReducer",
            "🎯 Filtering: sport='$selectedSport', date=$selectedDate, result=${filtered.size}"
        )

        return previousState.copy(
            selectedSport = selectedSport,
            filteredSchedule = filtered
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun reduceDateSelected(
        previousState: ScheduleUiState,
        schedule: List<ScheduleItem>,
        selectedDate: LocalDate
    ): ScheduleUiState {
        val filtered = schedule
            .filter { it.timestamp.toLocalDate() == selectedDate }
            .filter { previousState.selectedSport == null || it.title == previousState.selectedSport }

        return previousState.copy(
            selectedDate = selectedDate,
            filteredSchedule = filtered
        )
    }

    fun reduceScheduleCardIntent(
        state: ScheduleUiState,
        cardId: String,
        intent: ScheduleCardIntent
    ): ScheduleUiState {
        val currentCardState = state.cardStates[cardId] ?: ScheduleCardUiState()
        val newCardState = reduceCardUiState(currentCardState, intent)
        return state.copy(cardStates = state.cardStates.toMutableMap().apply {
            put(cardId, newCardState)
        })
    }
}
