package com.hfad.schedule.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.hfad.navigation.routes.ScheduleRoute
import com.hfad.schedule.ui.viewModel.ScheduleViewModel
import com.hfad.schedule.ui.screen.ScheduleScreen
import com.hfad.ui.LoginEventViewModel
import com.hfad.ui.SessionStateViewModel

@RequiresApi(Build.VERSION_CODES.O)
fun NavGraphBuilder.scheduleGraph(
    navController: NavController,
    loginEventViewModel: LoginEventViewModel
) {
    composable<ScheduleRoute> {
        val viewModel: ScheduleViewModel = hiltViewModel()
        val sessionViewModel: SessionStateViewModel = hiltViewModel()

        ScheduleScreen(
            viewModel = viewModel,
            sessionViewModel = sessionViewModel,
            loginEventViewModel = loginEventViewModel
        )
    }
}
