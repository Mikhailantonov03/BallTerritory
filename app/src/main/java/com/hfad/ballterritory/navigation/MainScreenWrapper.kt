package com.hfad.ballterritory.navigation

import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.hfad.coaches.navigation.CoachesFeature
import com.hfad.main.navigation.HomeFeature
import com.hfad.navigation.bottomNav.MainScreen
import com.hfad.profile.navigation.ProfileFeature
import com.hfad.rent.navigation.RentFeature
import com.hfad.schedule.navigation.ScheduleFeature
import com.hfad.ui.LoginEventViewModel

@Composable
fun MainScreenWrapper(
    onLoginRequested: () -> Unit
) {
    val navController = rememberNavController()
    val loginEventViewModel: LoginEventViewModel = hiltViewModel()
    val shouldNavigateToLogin by loginEventViewModel.shouldNavigateToLogin.collectAsState()

    // Инициализация фичей с ViewModel
    val profileFeature = remember { ProfileFeature(loginEventViewModel) }
    val scheduleFeature = remember { ScheduleFeature(loginEventViewModel) }

    // Навигация на экран логина при необходимости
    LaunchedEffect(shouldNavigateToLogin) {
        if (shouldNavigateToLogin) {
            onLoginRequested()
            loginEventViewModel.reset()
        }
    }

    MainScreen(
        navController = navController,
        startDestination = HomeFeature.route::class.qualifiedName!!,
        content = {
            registerFeature(HomeFeature, navController)
            registerFeature(scheduleFeature, navController)
            registerFeature(CoachesFeature, navController)
            registerFeature(RentFeature, navController)
            registerFeature(profileFeature, navController)
        }
    )
}
