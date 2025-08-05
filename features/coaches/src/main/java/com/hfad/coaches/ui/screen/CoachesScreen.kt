package com.hfad.coaches.ui.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.hfad.coaches.ui.mvi.CoachesIntent
import com.hfad.coaches.ui.mvi.CoachesState
import com.hfad.coaches.ui.mvi.CoachesViewModel
import com.hfad.module.Coach
import com.hfad.module.SportType
import kotlinx.coroutines.delay

@Composable
fun CoachesScreen(
    viewModel: CoachesViewModel = hiltViewModel(),
    onCoachClick: (Coach) -> Unit
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.onIntent(CoachesIntent.LoadCoaches)
    }

    when (state) {
        is CoachesState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    color = Color(0xFFFF9800), // ярко-оранжевый
                    trackColor = Color(0x33FFFFFF), // полупрозрачный белый
                    strokeWidth = 6.dp
                )
            }
        }

        is CoachesState.Error -> {
            val message = (state as CoachesState.Error).message
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Ошибка: $message", color = Color.Red)
            }
        }

        is CoachesState.Success -> {
            val coaches = (state as CoachesState.Success).coaches
            val basketballCoaches = coaches.filter { it.sport == SportType.BASKETBALL }
            val volleyballCoaches = coaches.filter { it.sport == SportType.VOLLEYBALL }

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFF1F2129))
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                item {
                    Spacer(modifier = Modifier.height(24.dp))
                    Text(
                        text = "Наши тренеры",
                        color = Color.White,
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                }

                item {
                    CoachSection("Тренеры по баскетболу", basketballCoaches, onCoachClick)
                }

                item {
                    Spacer(modifier = Modifier.height(16.dp))
                }

                item {
                    CoachSection("Тренеры по волейболу", volleyballCoaches, onCoachClick)
                }

                item {
                    Spacer(modifier = Modifier.height(64.dp))
                }
            }
        }
    }
}

@Composable
fun CoachSection(
    title: String,
    coaches: List<Coach>,
    onCoachClick: (Coach) -> Unit
) {
    Column {
        Text(
            text = title,
            color = Color.White,
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            items(coaches.size) { index ->
                val coach = coaches[index]
                var visible by remember { mutableStateOf(false) }

                LaunchedEffect(Unit) {
                    delay(100L * index)
                    visible = true
                }

                AnimatedVisibility(
                    visible = visible,
                    enter = fadeIn()
                ) {
                    CoachCard(coach = coach, onClick = { onCoachClick(coach) })
                }
            }
        }
    }
}
