package com.hfad.main.ui.screen.home

import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.hfad.main.ui.mvi.home.HomeIntent
import com.hfad.main.ui.mvi.home.HomeSingleEvent
import com.hfad.main.ui.mvi.home.HomeViewModel
import com.hfad.main.ui.screen.map.MapCard
import com.hfad.main.ui.screen.blog.BlogCard
import com.hfad.main.ui.screen.pass.PassCard
import kotlinx.coroutines.flow.collectLatest

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    navController: NavController
) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current
    var showContent by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        showContent = true
        viewModel.event.collectLatest { event ->
            when (event) {
                is HomeSingleEvent.NavigateToPass -> {
                    val url = "https://trbll.ru/terball-pass-pdf-print.pdf"
                    navController.navigate("pdf_pass?url=${Uri.encode(url)}")
                }
                is HomeSingleEvent.NavigateToArticle -> {
                    navController.navigate("webview?url=${Uri.encode(event.url)}")
                }
            }
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1F2129))
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(16.dp))
            AnimatedVisibility(
                visible = showContent,
                enter = fadeIn() + slideInVertically(initialOffsetY = { it / 2 })
            ) {
                PassCard(onClick = { viewModel.onIntent(HomeIntent.OnPassClicked) })
            }
        }

        item {
            AnimatedVisibility(
                visible = showContent,
                enter = fadeIn() + slideInVertically(initialOffsetY = { it / 2 })
            ) {
                Column {
                    Text(
                        text = "Найти нас на карте:",
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                    MapCard(onClick = {
                        navController.navigate("map_fullscreen")
                    })
                }
            }
        }

        itemsIndexed(state.articles) { index, article ->
            AnimatedVisibility(
                visible = showContent,
                enter = fadeIn() + slideInVertically(initialOffsetY = { it / 2 }),
            ) {
                BlogCard(
                    title = article.title,
                    subtitle = article.date,
                    imageUrl = article.imageUrl,
                    onClick = {
                        viewModel.onIntent(HomeIntent.OnArticleClicked(article.url))
                    }
                )
            }
        }

        item {
            Spacer(modifier = Modifier.height(72.dp))
        }
    }
}
