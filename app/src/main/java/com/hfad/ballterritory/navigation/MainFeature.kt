package com.hfad.ballterritory.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.hfad.navigation.bottomNav.MainScreen
import com.hfad.navigation.api.FeatureApi
import com.hfad.navigation.routes.MainRoute
import com.hfad.schedule.navigation.ScheduleFeature
import com.hfad.coaches.navigation.CoachesFeature
import com.hfad.main.navigation.HomeFeature
import com.hfad.rent.navigation.RentFeature
import com.hfad.profile.navigation.ProfileFeature

object MainFeature : FeatureApi {
    override val route = MainRoute

    override fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavController
    ) {
        navGraphBuilder.composable<MainRoute> {
            val innerNavController = rememberNavController()

            MainScreen(
                navController = innerNavController,
                startDestination = HomeFeature.route::class.qualifiedName!!, // Начальный таб
                content = {
                    registerFeature(HomeFeature, innerNavController)
                    registerFeature(ScheduleFeature, innerNavController)
                    registerFeature(CoachesFeature, innerNavController)
                    registerFeature(RentFeature, innerNavController)
                    registerFeature(ProfileFeature, innerNavController)
                }
            )
        }
    }
}
