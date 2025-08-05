package com.hfad.coaches.navigation


import kotlinx.serialization.Serializable


@Serializable
object CoachDetailRoute {
    const val ARG_COACH = "coach"
    val route = "coach_detail?$ARG_COACH={$ARG_COACH}"

    fun createRoute(coachJson: String): String {
        return "coach_detail?$ARG_COACH=$coachJson"
    }
}
