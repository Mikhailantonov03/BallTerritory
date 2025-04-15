package com.hfad.navigation.api


import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder

interface FeatureApi {
    val route: Any


    fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavController,

    )
}

