package com.hfad.ballterritory

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.lifecycleScope

import androidx.navigation.compose.rememberNavController
import com.hfad.auth.navigation.AuthFeature


import com.hfad.ballterritory.navigation.RootNavGraph
import com.hfad.data.token.tokens.SessionManager
import com.hfad.ballterritory.navigation.MainFeature


import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            val startDestination = if (sessionManager.isLoggedIn()) {

                 MainFeature.route
            } else {
                AuthFeature.route
            }

            setContent {
                val navController = rememberNavController()
                RootNavGraph(navController = navController , startDestination = startDestination)
            }
        }
    }
}
