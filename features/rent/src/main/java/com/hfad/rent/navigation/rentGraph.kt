package com.hfad.rent.navigation



import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.hfad.navigation.routes.RentRoute
import com.hfad.rent.ui.RentScreen


fun NavGraphBuilder.rentGraph(navController: NavController) {
    composable(RentRoute::class.qualifiedName!!) {
        RentScreen()
    }
}
