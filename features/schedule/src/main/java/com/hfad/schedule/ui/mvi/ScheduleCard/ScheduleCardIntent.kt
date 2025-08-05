package com.hfad.schedule.ui.mvi.ScheduleCard

sealed interface ScheduleCardIntent {
    data object ToggleExpand : ScheduleCardIntent
    data object ShowSignUpDialog : ScheduleCardIntent
    data object ShowCancelDialog : ScheduleCardIntent
    data object HideDialogs : ScheduleCardIntent
}