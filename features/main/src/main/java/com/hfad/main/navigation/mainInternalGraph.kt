package com.hfad.main.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.hfad.main.screen.MainTabScreen

import com.hfad.navigation.routes.MainRoute

fun NavGraphBuilder.mainInternalGraph(navController: NavController) {
    composable<MainRoute> {
        MainTabScreen()
    }
    registerFeature(ScheduleFeature, navController)
    registerFeature(CoachesFeature, navController)
    registerFeature(RentFeature, navController)
    registerFeature(ProfileFeature, navController)
}