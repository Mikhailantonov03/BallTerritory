package com.hfad.profile.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import com.hfad.navigation.api.FeatureApi
import com.hfad.navigation.routes.ProfileRoute
import com.hfad.ui.LoginEventViewModel

class ProfileFeature(
    private val loginEventViewModel: LoginEventViewModel
) : FeatureApi {

    override val route = ProfileRoute

    override fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavController
    ) {
        navGraphBuilder.profileGraph(navController, loginEventViewModel)
    }
}

