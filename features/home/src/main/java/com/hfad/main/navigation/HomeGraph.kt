package com.hfad.main.navigation

 import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
 import com.hfad.main.ui.screen.map.FullMapScreen
 import com.hfad.main.ui.screen.home.HomeScreen
import com.hfad.main.ui.screen.pass.PdfPassScreen
import com.hfad.main.ui.screen.home.WebViewScreen

import com.hfad.navigation.routes.HomeRoute


fun NavGraphBuilder.homeGraph(navController: NavController) {
    composable(HomeRoute::class.qualifiedName!!) {
        HomeScreen(navController = navController)
    }

    composable(
        "pdf_pass?url={url}",
        arguments = listOf(navArgument("url") { type = NavType.StringType })
    ) { backStackEntry ->
        val url = backStackEntry.arguments?.getString("url") ?: ""
        PdfPassScreen(url = url)
    }

    composable(
        "webview?url={url}",
        arguments = listOf(navArgument("url") { type = NavType.StringType })
    ) { backStackEntry ->
        val url = backStackEntry.arguments?.getString("url") ?: ""
        WebViewScreen(url = url)
    }

    composable("map_fullscreen") {
        FullMapScreen()
    }


}
