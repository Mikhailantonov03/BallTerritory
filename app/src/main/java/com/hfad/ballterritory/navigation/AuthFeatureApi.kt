package com.hfad.ballterritory.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import com.hfad.navigation.api.FeatureApi

fun NavGraphBuilder.registerFeature(
    feature: FeatureApi,
    navController: NavController
) {
    feature.registerGraph(this, navController)
}
