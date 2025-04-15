package com.hfad.main.navigation.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.hfad.navigation.routes.CoachesRoute
import com.hfad.navigation.routes.MainRoute
import com.hfad.navigation.routes.ProfileRoute
import com.hfad.navigation.routes.RentRoute
import com.hfad.navigation.routes.ScheduleRoute


@Composable
fun BottomNavBar(navController: NavHostController) {
    val items = listOf(
        BottomNavItem("Главная", Icons.Default.Home, MainRoute::class.qualifiedName!!),
        BottomNavItem("Расписание", Icons.Default.Event, ScheduleRoute::class.qualifiedName!!),
        BottomNavItem("Тренеры", Icons.Default.Group, CoachesRoute::class.qualifiedName!!),
        BottomNavItem("Аренда", Icons.Default.Place, RentRoute::class.qualifiedName!!),
        BottomNavItem("Профиль", Icons.Default.Person, ProfileRoute::class.qualifiedName!!)
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar {
        items.forEach { item ->
            NavigationBarItem(
                icon = { Icon(item.icon, contentDescription = null) },
                label = { Text(item.label) },
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}