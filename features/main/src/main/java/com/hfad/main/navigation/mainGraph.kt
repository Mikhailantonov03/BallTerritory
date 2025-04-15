package com.hfad.main.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.hfad.main.screen.MainScreen
import com.hfad.navigation.routes.MainRoute

fun NavGraphBuilder.mainGraph(navController: NavController) {
    composable(MainRoute::class.qualifiedName!!) {
        MainScreen()
    }
}
