    package com.hfad.schedule.navigation

    import androidx.navigation.NavController
    import androidx.navigation.NavGraphBuilder
    import com.hfad.navigation.api.FeatureApi
    import com.hfad.navigation.routes.ScheduleRoute

    object ScheduleFeature : FeatureApi {
        override val route = ScheduleRoute

        override fun registerGraph(navGraphBuilder: NavGraphBuilder, navController: NavController) {
            navGraphBuilder.scheduleGraph(navController)
        }
    }