package com.hfad.coaches.ui.mvi

sealed class CoachesIntent {
    object LoadCoaches : CoachesIntent()
    data class OnCoachClick(val coachId: String) : CoachesIntent()
}
