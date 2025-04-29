package com.hfad.ballterritory.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import com.hfad.navigation.api.FeatureApi
import com.hfad.profile.navigation.ProfileFeature

fun NavGraphBuilder.registerFeature(
    feature: FeatureApi,
    navController: NavController,
    onLoginRequested: (() -> Unit)? = null
) {
    when (feature) {
        is MainFeature -> feature.registerGraph(this, navController, onLoginRequested!!)
        is ProfileFeature -> feature.registerGraph(this, navController)
        else -> feature.registerGraph(this, navController)
    }
}
