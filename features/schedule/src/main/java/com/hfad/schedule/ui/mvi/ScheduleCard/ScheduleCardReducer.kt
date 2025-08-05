package com.hfad.schedule.ui.mvi.ScheduleCard

 import com.hfad.schedule.ui.mvi.Schedule.ScheduleUiState

fun reduceCardUiState(
    state: ScheduleCardUiState,
    intent: ScheduleCardIntent
): ScheduleCardUiState = when (intent) {
    is ScheduleCardIntent.ToggleExpand -> state.copy(isExpanded = !state.isExpanded)
    is ScheduleCardIntent.ShowSignUpDialog -> state.copy(showSignUpDialog = true)
    is ScheduleCardIntent.ShowCancelDialog -> state.copy(showCancelDialog = true)
    is ScheduleCardIntent.HideDialogs -> state.copy(showSignUpDialog = false, showCancelDialog = false)
}



