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
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.hfad.navigation.extensions.navigateToRoute
import com.hfad.navigation.routes.CoachesRoute
import com.hfad.navigation.routes.HomeRoute
import com.hfad.navigation.routes.ProfileRoute
import com.hfad.navigation.routes.RentRoute
import com.hfad.navigation.routes.ScheduleRoute

@Composable
fun BottomNavBar(navController: NavController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val bottomNavRoutes = listOf(
        HomeRoute::class.qualifiedName!!,
        ScheduleRoute::class.qualifiedName!!,
        CoachesRoute::class.qualifiedName!!,
        RentRoute::class.qualifiedName!!,
        ProfileRoute::class.qualifiedName!!
    )


    if (currentRoute !in bottomNavRoutes) return

    val items = listOf(
        BottomNavItem("Главная", Icons.Default.Home, HomeRoute::class.qualifiedName!!),
        BottomNavItem("Расписание", Icons.Default.DateRange, ScheduleRoute::class.qualifiedName!!),
        BottomNavItem("Тренеры", Icons.Default.Info, CoachesRoute::class.qualifiedName!!),
        BottomNavItem("Аренда", Icons.Default.Place, RentRoute::class.qualifiedName!!),
        BottomNavItem("Профиль", Icons.Default.Person, ProfileRoute::class.qualifiedName!!)
    )

    NavigationBar {
        items.forEach { item ->
            NavigationBarItem(
                selected = currentRoute == item.route,
                onClick = {
                    if (currentRoute == item.route) return@NavigationBarItem
                    navController.navigateToRoute(item.route, popUpToStart = false)
                },
                icon = { Icon(item.icon, contentDescription = item.label) },
                label = { Text(item.label) }
            )
        }
    }
}
