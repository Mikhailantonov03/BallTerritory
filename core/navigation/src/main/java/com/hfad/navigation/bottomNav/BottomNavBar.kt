package com.hfad.navigation.bottomNav

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange

import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Place

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.hfad.navigation.routes.CoachesRoute
import com.hfad.navigation.routes.HomeRoute
import com.hfad.navigation.routes.ProfileRoute
import com.hfad.navigation.routes.RentRoute
import com.hfad.navigation.routes.ScheduleRoute

@Composable
fun BottomNavBar(navController: NavController) {
    val items = listOf(
        BottomNavItem("Главная", Icons.Default.Home, HomeRoute::class.qualifiedName!!),
        BottomNavItem("Расписание", Icons.Default.DateRange, ScheduleRoute::class.qualifiedName!!), // заменили
        BottomNavItem("Тренеры", Icons.Default.Info, CoachesRoute::class.qualifiedName!!), // заменили
        BottomNavItem("Аренда", Icons.Default.Place, RentRoute::class.qualifiedName!!),
        BottomNavItem("Профиль", Icons.Default.Person, ProfileRoute::class.qualifiedName!!)
    )

    NavigationBar {
        val currentRoute = navController.currentDestination?.route
        items.forEach { item ->
            NavigationBarItem(
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = { Icon(item.icon, contentDescription = item.label) },
                label = { Text(item.label) }
            )
        }
    }
}
