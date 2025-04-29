package com.hfad.ballterritory.navigation

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.hfad.auth.navigation.AuthFeature
import com.hfad.navigation.routes.AuthStartRoute
import com.hfad.ui.LoginEventViewModel


@SuppressLint("UnrememberedGetBackStackEntry")
@Composable
fun RootNavGraph(
    navController: NavHostController,
    startDestination: Any = AuthFeature.route
) {
    NavHost(
        navController = navController,
        startDestination = startDestination::class.qualifiedName!!
    ) {
        registerFeature(AuthFeature, navController)
        registerFeature(MainFeature, navController) {
            navController.navigate(AuthStartRoute::class.qualifiedName!!) {
                popUpTo(0) { inclusive = true }
                launchSingleTop = true
            }
        }
    }
}

