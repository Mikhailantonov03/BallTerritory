package com.hfad.profile.navigation

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.hfad.navigation.extensions.navigateTo
import com.hfad.navigation.routes.ProfileRoute
import com.hfad.profile.ui.ProfileScreen
import com.hfad.profile.ui.screen.*
import com.hfad.profile.ui.screen.history.HistoryScreen
import com.hfad.profile.ui.screen.profile.GuestProfileScreen
import com.hfad.profile.ui.viewModel.ProfileViewModel
import com.hfad.ui.LoginEventViewModel
import com.hfad.ui.SessionStateViewModel

fun NavGraphBuilder.profileGraph(
    navController: NavController,
    loginEventViewModel: LoginEventViewModel
) {
    composable<ProfileRoute> {
        val sessionViewModel: SessionStateViewModel = hiltViewModel()
        val sessionState by sessionViewModel.sessionState.collectAsState()

        when {
            sessionState.isGuest -> {
                GuestProfileScreen(
                    onLoginClick = {
                        loginEventViewModel.requestLogin()
                    }
                )
            }

            sessionState.isFullyAuthorized -> {
                val profileViewModel: ProfileViewModel = hiltViewModel()
                ProfileScreen(
                    viewModel = profileViewModel,
                    onLoggedOut = {
                        loginEventViewModel.requestLogin()
                    },
                    onHistoryClick = { navController.navigateTo<HistoryRoute>() },
                    onSocialsClick = { navController.navigateTo<SocialsRoute>() },
                    onContactsClick = { navController.navigateTo<ContactsRoute>() },
                    onAboutClick = { navController.navigateTo<AboutRoute>() }
                )
            }

            else -> {
                // Можно показать прогресс-бар или заглушку
            }
        }
    }

    composable<HistoryRoute> { HistoryScreen() }
    composable<SocialsRoute> { SocialsScreen() }
    composable<ContactsRoute> { ContactsScreen() }
    composable<AboutRoute> { AboutScreen() }
}
