package com.hfad.schedule.ui.mvi.ScheduleCard

data class ScheduleCardUiState(
    val isExpanded: Boolean = false,
    val showSignUpDialog: Boolean = false,
    val showCancelDialog: Boolean = false
)