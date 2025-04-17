package com.hfad.navigation.extensions

import androidx.navigation.NavController


fun NavController.navigateToRoute(
    route: String,
    popUpToStart: Boolean = true,
    inclusive: Boolean = false
) {
    this.navigate(route) {
        if (popUpToStart) {
            popUpTo(this@navigateToRoute.graph.startDestinationId) {
                this.inclusive = inclusive
                this.saveState = true
            }
        }
        launchSingleTop = true
        restoreState = true
    }
}

