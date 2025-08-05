package com.hfad.main.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import com.hfad.navigation.api.FeatureApi
import com.hfad.navigation.routes.HomeRoute


object HomeFeature : FeatureApi {
    override val route = HomeRoute

    override fun registerGraph(navGraphBuilder: NavGraphBuilder, navController: NavController) {
        navGraphBuilder.homeGraph(navController)
    }
}
