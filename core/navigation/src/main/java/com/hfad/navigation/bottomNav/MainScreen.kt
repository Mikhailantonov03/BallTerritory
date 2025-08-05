    package com.hfad.navigation.bottomNav


    import androidx.compose.foundation.layout.Box
    import androidx.compose.foundation.layout.fillMaxSize
    import androidx.compose.foundation.layout.padding
    import androidx.compose.material3.Scaffold
    import androidx.compose.runtime.Composable
    import androidx.compose.ui.Modifier
    import androidx.compose.ui.graphics.Color
    import androidx.compose.ui.unit.dp
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
            containerColor = Color(0xFF1F2129), // ← тёмный фон под всем
            bottomBar = {
                // Добавим немного отступа снизу, чтобы BottomNavBar выглядел полукруглым
                Box(modifier = Modifier.padding(bottom = 12.dp)) {
                    BottomNavBar(navController = navController)
                }
            }
        ) { innerPadding ->
            // ВАЖНО: передаём отступы, чтобы контент не оказался под навбаром
            Box(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
            ) {
                NavHost(
                    navController = navController,
                    startDestination = startDestination,
                    modifier = Modifier.fillMaxSize(),
                    builder = content
                )
            }
        }
    }
