
    package com.hfad.auth.navigation

    import android.annotation.SuppressLint
    import androidx.compose.runtime.collectAsState
    import androidx.compose.runtime.getValue
    import androidx.compose.runtime.remember
    import androidx.hilt.navigation.compose.hiltViewModel
    import androidx.navigation.NavController
    import androidx.navigation.NavGraphBuilder
    import androidx.navigation.compose.composable
    import com.hfad.auth.ui.mvi.AuthIntents
    import com.hfad.auth.ui.screen.EnterCodeScreen
    import com.hfad.auth.ui.screen.EnterPhoneScreen
    import com.hfad.auth.ui.screen.ProfileCompletionScreen
    import com.hfad.auth.ui.screen.StartScreen
    import com.hfad.auth.ui.viewModels.AuthViewModel
    import com.hfad.navigation.extensions.navigateTo
    import com.hfad.navigation.routes.AuthStartRoute
    import com.hfad.navigation.routes.EnterCodeRoute
    import com.hfad.navigation.routes.EnterPhoneRoute
    import com.hfad.navigation.routes.MainRoute
    import com.hfad.navigation.routes.ProfileCompletionRoute

    @SuppressLint("UnrememberedGetBackStackEntry")
    fun NavGraphBuilder.authGraph(
        navController: NavController
    ) {
        composable<AuthStartRoute> {
            val viewModel: AuthViewModel = hiltViewModel()

            StartScreen(
                onStart = { navController.navigateTo<EnterPhoneRoute>(popUpToStart = true, inclusive = true) },
                onSkipClick = {
                    viewModel.markAsGuest {
                        navController.navigateTo<MainRoute>(popUpToStart = true, inclusive = true)
                    }
                }
            )
        }

            composable<EnterPhoneRoute> {
            val parentEntry = remember {
                navController.getBackStackEntry(AuthStartRoute::class.qualifiedName!!)
            }
            val viewModel: AuthViewModel = hiltViewModel(parentEntry)
            val state by viewModel.uiState.collectAsState()

            EnterPhoneScreen(
                uiState = state,
                onPhoneEntered = { phone ->
                    viewModel.onIntent(AuthIntents.OnPhoneEntered(phone))
                },
                onNavigateToCode = {
                    navController.navigateTo<EnterCodeRoute>(popUpToStart = true, inclusive = true)
                }
            )
        }

        composable<EnterCodeRoute> {
            val parentEntry = remember {
                navController.getBackStackEntry(AuthStartRoute::class.qualifiedName!!)
            }
            val viewModel: AuthViewModel = hiltViewModel(parentEntry)
            val state by viewModel.uiState.collectAsState()

            EnterCodeScreen(
                uiState = state,
                onCodeEntered = { code ->
                    viewModel.onIntent(AuthIntents.OnCodeEntered(code))
                },
                onNavigateToProfileSetup = {
                    navController.navigateTo<ProfileCompletionRoute>(popUpToStart = true, inclusive = true)
                }
            )
        }
        composable<ProfileCompletionRoute> {
            val parentEntry = remember {
                navController.getBackStackEntry(AuthStartRoute::class.qualifiedName!!)
            }
            val viewModel: AuthViewModel = hiltViewModel(parentEntry)
            val state by viewModel.uiState.collectAsState()

            ProfileCompletionScreen(
                uiState = state,
                onComplete = { name,email ->
                    viewModel.onIntent(AuthIntents.OnProfileCompleted(name,email))

                    navController.navigateTo<MainRoute>(popToRoot = true)
                }
            )
        }



    }
