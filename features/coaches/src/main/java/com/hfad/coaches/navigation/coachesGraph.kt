package com.hfad.coaches.navigation

import android.net.Uri
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.hfad.coaches.ui.screen.CoachDetailScreen
import com.hfad.coaches.ui.screen.CoachesScreen
import com.hfad.module.Coach
import com.hfad.navigation.routes.CoachesRoute
import kotlinx.serialization.json.Json
import androidx.navigation.NavType
import androidx.navigation.navArgument


fun NavGraphBuilder.coachesGraph(navController: NavController) {
    composable(CoachesRoute::class.qualifiedName!!) {
        CoachesScreen(
            onCoachClick = { coach ->
                val json = Uri.encode(Json.encodeToString(Coach.serializer(), coach))
                navController.navigate(CoachDetailRoute.createRoute(json))
            }
        )
    }

    composable(
        route = CoachDetailRoute.route,
        arguments = listOf(navArgument(CoachDetailRoute.ARG_COACH) {
            type = NavType.StringType
        })
    ) { backStackEntry ->
        val json = backStackEntry.arguments?.getString(CoachDetailRoute.ARG_COACH)
        json?.let {
            val coach = Json.decodeFromString<Coach>(Uri.decode(it))
            CoachDetailScreen(coach = coach, onBack = { navController.popBackStack() })
        }
    }
}
