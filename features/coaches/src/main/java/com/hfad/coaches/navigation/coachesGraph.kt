package com.hfad.coaches.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.hfad.coaches.ui.screen.CoachesScreen
import com.hfad.navigation.routes.CoachesRoute

fun NavGraphBuilder.coachesGraph(navController: NavController) {
    composable(CoachesRoute::class.qualifiedName!!) {
        CoachesScreen()
    }
}
