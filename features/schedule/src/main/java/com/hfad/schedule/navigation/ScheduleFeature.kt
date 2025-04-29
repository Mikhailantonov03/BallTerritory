    package com.hfad.schedule.navigation

    import android.os.Build
    import androidx.annotation.RequiresApi
    import androidx.navigation.NavController
    import androidx.navigation.NavGraphBuilder
    import com.hfad.navigation.api.FeatureApi
    import com.hfad.navigation.routes.ScheduleRoute
    import com.hfad.ui.LoginEventViewModel

    class ScheduleFeature(
        private val loginEventViewModel: LoginEventViewModel
    ) : FeatureApi {

        override val route = ScheduleRoute

        @RequiresApi(Build.VERSION_CODES.O)
        override fun registerGraph(
            navGraphBuilder: NavGraphBuilder,
            navController: NavController
        ) {
            navGraphBuilder.scheduleGraph(navController, loginEventViewModel)
        }
    }
