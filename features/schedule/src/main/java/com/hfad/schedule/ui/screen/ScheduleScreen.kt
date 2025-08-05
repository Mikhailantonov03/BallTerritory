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
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
 import com.hfad.module.UiStatus
import com.hfad.schedule.ui.mvi.Schedule.ScheduleIntents
import com.hfad.schedule.ui.model.SportFilterItem
import com.hfad.schedule.ui.mvi.Schedule.toFormattedDate
import com.hfad.schedule.ui.mvi.Schedule.toLocalDate
import com.hfad.schedule.ui.mvi.ScheduleCard.ScheduleCardIntent
import com.hfad.schedule.ui.mvi.ScheduleCard.ScheduleCardUiState
import com.hfad.schedule.ui.viewModel.ScheduleViewModel
import com.hfad.ui.LoginEventViewModel
import com.hfad.ui.SessionState
import com.hfad.ui.SessionStateViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale


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
    val today = remember { LocalDate.now() }
    val initialPage = 1000
    val pagerState = rememberPagerState(initialPage = initialPage, pageCount = { Int.MAX_VALUE })
    val coroutineScope = rememberCoroutineScope()

    val pageDate = remember(pagerState.currentPage) {
        today.plusDays((pagerState.currentPage - initialPage).toLong())
    }

    LaunchedEffect(pageDate) {
        viewModel.onIntent(ScheduleIntents.OnDateSelected(pageDate))
        viewModel.onIntent(ScheduleIntents.LoadScheduleForDate(pageDate))
    }

    PullToRefreshBox(
        isRefreshing = uiState.status is UiStatus.Loading,
        onRefresh = { viewModel.onIntent(ScheduleIntents.LoadScheduleForDate(pageDate)) },
        state = pullRefreshState,
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF272933))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF1F2129))
                .padding(bottom = 8.dp)
        ) {
            Spacer(modifier = Modifier.height(8.dp))

            CalendarHeader(
                currentDate = pageDate,
                selectedDate = pageDate,
                onDateSelected = { selected ->
                    val offset = selected.toEpochDay() - today.toEpochDay()
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(initialPage + offset.toInt())
                    }
                }
            )

            if (sportFilters.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                SportFilterRow(
                    filters = sportFilters,
                    selectedSport = uiState.selectedSport,
                    onSportSelected = {
                        if (it != uiState.selectedSport) {
                            viewModel.onIntent(ScheduleIntents.OnSportFilterSelected(it))
                        }
                    }
                )
            }

            HorizontalPager(
                state = pagerState,
                modifier = Modifier.weight(1f)
            ) { page ->
                val dateForPage = today.plusDays((page - initialPage).toLong())
                val scheduleForDay = uiState.filteredSchedule.filter {
                    it.timestamp.toLocalDate() == dateForPage
                }
                val bookedIds = remember(bookings) { bookings.map { it.scheduleItemId }.toSet() }
                val visibilityMap = remember { mutableStateMapOf<String, Boolean>() }

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp)
                        .padding(top = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    if (uiState.status is UiStatus.Error) {
                        item {
                            Text(
                                text = (uiState.status as UiStatus.Error).message,
                                color = MaterialTheme.colorScheme.error
                            )
                        }
                    } else if (scheduleForDay.isEmpty()) {
                        item {
                            Text("На этот день нет занятий", color = Color.White)
                        }
                    } else {
                        itemsIndexed(scheduleForDay, key = { _, item -> item.id }) { _, item ->
                            LaunchedEffect(item.id) {
                                delay(50)
                                visibilityMap[item.id] = true
                            }
                            AnimatedVisibility(
                                visible = visibilityMap[item.id] == true,
                                enter = fadeIn() + slideInVertically(initialOffsetY = { it / 2 }),
                            ) {
                                ScheduleCard(
                                    item = item,
                                    isBooked = item.id in bookedIds,
                                    sessionState = sessionState,
                                    cardState = uiState.cardStates[item.id] ?: ScheduleCardUiState(),
                                    onIntent = {
                                        viewModel.onIntent(
                                            ScheduleIntents.OnScheduleCardIntent(
                                                cardId = item.id,
                                                intent = it
                                            )
                                        )
                                    },
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
    currentDate: LocalDate,
    selectedDate: LocalDate,
    onDateSelected: (LocalDate) -> Unit
) {
    val days = remember(currentDate) {
        (0..6).map { currentDate.plusDays(it.toLong()) }
    }

    LazyRow(
        modifier = Modifier.padding(horizontal = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        itemsIndexed(days) { index, day ->
            val visible = remember { mutableStateOf(false) }

            LaunchedEffect(Unit) {
                delay(40L * index)
                visible.value = true
            }

            AnimatedVisibility(
                visible = visible.value,
                enter = fadeIn()
            ) {
                Button(
                    onClick = { onDateSelected(day) },
                    shape = RoundedCornerShape(24.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (day == selectedDate) Color(0xFF2D313A) else Color(0xFF1F2129),
                        contentColor = Color.White
                    ),
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp)
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = day.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale("ru")).uppercase(),
                            color = Color.White
                        )
                        Text(
                            text = day.dayOfMonth.toString(),
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
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
    LazyRow(
        modifier = Modifier.padding(horizontal = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            FilterChip(
                text = "Все (${filters.sumOf { it.count }})",
                isSelected = selectedSport == null,
                onClick = { onSportSelected(null) }
            )
        }

        itemsIndexed(filters) { index, filter ->
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
            .clip(RoundedCornerShape(24.dp))
            .background(
                if (isSelected) Color(0xFFFF4F4F) // серый фон для выбранного
                else Color(0xFF272933)
            )
            .clickable { onClick() }
            .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 16.dp)

    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            color = Color.White
        )
    }
}
