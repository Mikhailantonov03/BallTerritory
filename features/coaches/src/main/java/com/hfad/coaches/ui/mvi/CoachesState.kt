package com.hfad.coaches.ui.mvi

import com.hfad.module.Coach

sealed class CoachesState {
    object Loading : CoachesState()
    data class Success(val coaches: List<Coach>) : CoachesState()
    data class Error(val message: String) : CoachesState()
}
