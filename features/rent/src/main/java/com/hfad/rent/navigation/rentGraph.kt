package com.hfad.rent.navigation



import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.hfad.navigation.routes.RentRoute
import com.hfad.rent.ui.screen.RentScreen
import com.hfad.rent.ui.viewModel.RentViewModel


fun NavGraphBuilder.rentGraph(navController: NavController) {
    composable<RentRoute> {
        val viewModel: RentViewModel = hiltViewModel()
        RentScreen(viewModel = viewModel)
    }
}
