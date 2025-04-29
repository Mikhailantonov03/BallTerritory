package com.hfad.ballterritory.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

import com.hfad.navigation.api.FeatureApi
import com.hfad.navigation.routes.MainRoute


object MainFeature : FeatureApi {
    override val route = MainRoute

     override fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavController
    ) {
        error("You must call registerGraph with onLoginRequested for MainFeature")
    }

     fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavController,
        onLoginRequested: () -> Unit
    ) {
        navGraphBuilder.composable<MainRoute> {
            MainScreenWrapper(onLoginRequested = onLoginRequested)
        }
    }
}
