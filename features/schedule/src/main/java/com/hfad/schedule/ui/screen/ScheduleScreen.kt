package com.hfad.schedule.ui.screen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.hfad.module.UiStatus
import com.hfad.schedule.ui.mvi.ScheduleIntents
import com.hfad.schedule.ui.model.ScheduleItem
import com.hfad.schedule.ui.model.SportFilterItem
import com.hfad.schedule.ui.mvi.toFormattedDate
import com.hfad.schedule.ui.mvi.toLocalDate
import com.hfad.schedule.ui.viewModel.ScheduleViewModel
import com.hfad.ui.LoginEventViewModel
import com.hfad.ui.SessionState
import com.hfad.ui.SessionStateViewModel
import kotlinx.coroutines.delay
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ScheduleScreen(
    viewModel: ScheduleViewModel,
    sessionViewModel: SessionStateViewModel,
    loginEventViewModel: LoginEventViewModel
) {
    val uiState by viewModel.uiState.collectAsState()
    val sessionState by sessionViewModel.sessionState.collectAsState()
    val bookings by viewModel.bookings.collectAsState()
    val sportFilters by viewModel.sportFilters.collectAsState()

    val pullRefreshState = rememberPullToRefreshState()

    LaunchedEffect(Unit) {
        viewModel.onIntent(ScheduleIntents.LoadScheduleForDate(uiState.selectedDate))
    }

    PullToRefreshBox(
        isRefreshing = uiState.status is UiStatus.Loading,
        onRefresh = { viewModel.onIntent(ScheduleIntents.LoadScheduleForDate(uiState.selectedDate))
        },
        state = pullRefreshState,
        modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                CalendarHeader(
                    selectedDate = uiState.selectedDate,
                    onDateSelected = { viewModel.onIntent(ScheduleIntents.OnDateSelected(it)) }
                )
            }

            item {
                if (sportFilters.isNotEmpty()) {
                    SportFilterRow(
                        filters = sportFilters,
                        selectedSport = uiState.selectedSport,
                        onSportSelected = { viewModel.onIntent(ScheduleIntents.OnSportFilterSelected(it)) }
                    )
                }
            }

            when (val status = uiState.status) {
                is UiStatus.Loading -> {
                    // При PullToRefreshBox отдельно показывать CircularProgressIndicator не нужно
                    // он уже встроен в PullToRefreshBox
                }

                is UiStatus.Error -> {
                    item {
                        Text(
                            text = status.message,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }

                else -> {
                    val scheduleForDay = uiState.filteredSchedule.filter {
                        it.timestamp.toLocalDate() == uiState.selectedDate
                    }

                    val bookedIds = bookings.map { it.scheduleItemId }

                    if (scheduleForDay.isEmpty()) {
                        item {
                            Text("На этот день нет занятий")
                        }
                    } else {
                        items(scheduleForDay, key = { it.id }) { item ->
                            val isVisible = remember { mutableStateOf(false) }
                            LaunchedEffect(Unit) {
                                isVisible.value = true
                                delay(50)
                            }

                            AnimatedVisibility(
                                visible = isVisible.value,
                                enter = fadeIn() + slideInVertically(initialOffsetY = { it / 2 }),
                            )
                            {
                            ScheduleCard(
                                item = item,
                                isBooked = item.id in bookedIds,
                                sessionState = sessionState,
                                onSignUp = { viewModel.onIntent(ScheduleIntents.OnSignUpClick(it)) },
                                onCancel = { viewModel.onIntent(ScheduleIntents.OnCancelClick(it)) },
                                onLoginRequired = { loginEventViewModel.requestLogin() }
                            )
                        }
                        }
                    }
                }
            }
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CalendarHeader(
    selectedDate: LocalDate,
    onDateSelected: (LocalDate) -> Unit
) {
    val today = remember { LocalDate.now() }
    val days = remember(today) {
        (0..6).map { today.plusDays(it.toLong()) }
    }
    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        items(days) { day ->
            OutlinedButton(
                onClick = { onDateSelected(day) },
                colors = if (day == selectedDate)
                    ButtonDefaults.outlinedButtonColors(containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f))
                else ButtonDefaults.outlinedButtonColors()
            ) {
                Text("${day.dayOfWeek.name.take(3)}\n${day.dayOfMonth}")
            }
        }
    }
}

@Composable
fun SportFilterRow(
    filters: List<SportFilterItem>,
    selectedSport: String?,
    onSportSelected: (String?) -> Unit
) {
    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        item {
            FilterChip(
                text = "Все (${filters.sumOf { it.count }})",
                isSelected = selectedSport == null,
                onClick = { onSportSelected(null) }
            )
        }

        items(filters) { filter ->
            FilterChip(
                text = "${filter.title} (${filter.count})",
                isSelected = selectedSport == filter.title,
                onClick = { onSportSelected(filter.title) }
            )
        }
    }
}

@Composable
fun FilterChip(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .clip(MaterialTheme.shapes.medium)
            .background(
                if (isSelected) MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
                else MaterialTheme.colorScheme.surfaceVariant
            )
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
fun ScheduleCard(
    item: ScheduleItem,
    isBooked: Boolean,
    sessionState: SessionState,
    onSignUp: (String) -> Unit,
    onCancel: (String) -> Unit,
    onLoginRequired: () -> Unit
) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(item.title, style = MaterialTheme.typography.titleMedium)
            Text("Время: ${item.timestamp.toFormattedDate()}")
            Text("Тренер: ${item.trainer}")

            Spacer(modifier = Modifier.height(8.dp))

            when {
                sessionState.isFullyAuthorized && isBooked -> {
                    Button(onClick = { onCancel(item.id) }) {
                        Text("Отменить запись")
                    }
                }

                sessionState.isFullyAuthorized -> {
                    Button(onClick = { onSignUp(item.id) }) {
                        Text("Записаться")
                    }
                }

                else -> {
                    OutlinedButton(onClick = onLoginRequired) {
                        Text("Войти, чтобы записаться")
                    }
                }
            }
        }
    }
}
