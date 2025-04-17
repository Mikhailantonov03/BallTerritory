package com.hfad.ballterritory.navigation


import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.hfad.coaches.navigation.CoachesFeature
import com.hfad.main.navigation.HomeFeature
import com.hfad.navigation.bottomNav.MainScreen
import com.hfad.profile.navigation.ProfileFeature
import com.hfad.rent.navigation.RentFeature
import com.hfad.schedule.navigation.ScheduleFeature

@Composable
fun MainScreenWrapper() {
    val navController = rememberNavController()

    MainScreen(
        navController = navController,
        startDestination = HomeFeature.route::class.qualifiedName!!,
        content = {
            registerFeature(HomeFeature, navController)
            registerFeature(ScheduleFeature, navController)
            registerFeature(CoachesFeature, navController)
            registerFeature(RentFeature, navController)
            registerFeature(ProfileFeature, navController)
        }
    )
}
