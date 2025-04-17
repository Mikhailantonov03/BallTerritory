package com.hfad.ballterritory.navigation

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.hfad.auth.navigation.AuthFeature


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
        registerFeature(MainFeature, navController)

    }
}   

