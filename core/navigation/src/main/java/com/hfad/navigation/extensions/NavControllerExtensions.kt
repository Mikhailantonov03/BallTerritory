package com.hfad.navigation.extensions

import androidx.navigation.NavController
import androidx.navigation.navOptions


inline fun <reified T : Any> NavController.navigateTo(
    launchSingleTop: Boolean = true,
    restoreState: Boolean = true,
    popUpToStart: Boolean = false,
    inclusive: Boolean = false,
    popToRoot: Boolean = false // 👈 добавили!
) {
    val route = T::class.qualifiedName ?: error("No route for ${T::class}")

    val options = navOptions {
        this.launchSingleTop = launchSingleTop
        this.restoreState = restoreState
        if (popToRoot) {
            popUpTo(0) {
                this.inclusive = true
                this.saveState = false
            }
        } else if (popUpToStart) {
            popUpTo(graph.startDestinationId) {
                this.inclusive = inclusive
                this.saveState = true
            }
        }
    }

    this.navigate(route, options)
}

