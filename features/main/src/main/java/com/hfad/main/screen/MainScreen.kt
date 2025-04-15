package com.hfad.main.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.hfad.main.navigation.mainInternalGraph
import com.hfad.main.navigation.ui.BottomNavBar
import com.hfad.navigation.routes.MainRoute

@Composable
fun MainScreen() {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomNavBar(navController = navController)
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = MainRoute::class.qualifiedName!!,
            modifier = Modifier.padding(innerPadding)
        ) {
            mainInternalGraph(navController)
        }
    }
}

