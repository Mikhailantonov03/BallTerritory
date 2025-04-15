    package com.hfad.navigation.bottomNav


    import androidx.compose.foundation.layout.padding
    import androidx.compose.material3.Scaffold
    import androidx.compose.runtime.Composable
    import androidx.compose.ui.Modifier
    import androidx.navigation.NavGraphBuilder
    import androidx.navigation.NavHostController
    import androidx.navigation.compose.NavHost


    @Composable
    fun MainScreen(
        navController: NavHostController,
        startDestination: String,
        content: NavGraphBuilder.() -> Unit
    ) {
        Scaffold(
            bottomBar = { BottomNavBar(navController = navController) }
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = startDestination,
                modifier = Modifier.padding(innerPadding),
                builder = content
            )
        }
    }
