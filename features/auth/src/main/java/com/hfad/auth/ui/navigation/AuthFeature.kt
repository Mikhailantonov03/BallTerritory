package com.hfad.auth.ui.navigation


import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import com.hfad.navigation.api.FeatureApi
import com.hfad.navigation.routes.AuthStartRoute

object AuthFeature : FeatureApi {
    override val route = AuthStartRoute


    override fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavController,

    ) {
        navGraphBuilder.authGraph(navController)
    }
}


