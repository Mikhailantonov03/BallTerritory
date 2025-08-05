package com.hfad.rent.navigation



import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import com.hfad.navigation.api.FeatureApi
import com.hfad.navigation.routes.RentRoute

object RentFeature : FeatureApi {
    override val route = RentRoute

    override fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavController
    ) {
        navGraphBuilder.rentGraph(navController)
    }
}
