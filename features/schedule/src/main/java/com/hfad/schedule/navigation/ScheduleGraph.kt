package com.hfad.schedule.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.hfad.navigation.routes.ScheduleRoute
import com.hfad.schedule.ui.screen.ScheduleScreen

fun NavGraphBuilder.scheduleGraph(navController: NavController) {
    composable(ScheduleRoute::class.qualifiedName!!) {
        ScheduleScreen()
    }
}