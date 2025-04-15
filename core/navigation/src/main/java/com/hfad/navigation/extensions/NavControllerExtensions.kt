package com.hfad.navigation.extensions

import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.navOptions

inline fun <reified T : Any> NavController.navigateTo(
    navOptions: NavOptions? = navOptions {}
) {
    this.navigate(T::class.qualifiedName ?: error("No route for ${T::class}"), navOptions)
}
