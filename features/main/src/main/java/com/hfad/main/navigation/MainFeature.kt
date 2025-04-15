package com.hfad.main.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import com.hfad.navigation.api.FeatureApi
import com.hfad.navigation.routes.MainRoute

object MainFeature : FeatureApi {
    override val route = MainRoute

    override fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavController
    ) {
        navGraphBuilder.mainGraph(navController)
    }
}
