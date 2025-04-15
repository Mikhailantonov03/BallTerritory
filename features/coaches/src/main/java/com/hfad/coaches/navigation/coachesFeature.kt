package com.hfad.coaches.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import com.hfad.navigation.api.FeatureApi
import com.hfad.navigation.routes.CoachesRoute

object CoachesFeature : FeatureApi {
    override val route = CoachesRoute

    override fun registerGraph(navGraphBuilder: NavGraphBuilder, navController: NavController) {
        navGraphBuilder.coachesGraph(navController)
    }
}
