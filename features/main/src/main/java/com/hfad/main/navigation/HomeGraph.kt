package com.hfad.main.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.hfad.main.ui.screen.HomeScreen

import com.hfad.navigation.routes.HomeRoute


fun NavGraphBuilder.homeGraph(navController: NavController) {
    composable(HomeRoute::class.qualifiedName!!) {
        HomeScreen()
    }
}
