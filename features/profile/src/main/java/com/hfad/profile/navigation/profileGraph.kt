package com.hfad.profile.navigation


import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.hfad.navigation.routes.ProfileRoute
import com.hfad.profile.ui.ProfileScreen


fun NavGraphBuilder.profileGraph(navController: NavController) {
    composable(ProfileRoute::class.qualifiedName!!) {
        ProfileScreen()
    }
}
