package com.hfad.schedule.ui.viewModel

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hfad.schedule.domain.usecase.BookClassUseCase
import com.hfad.schedule.domain.usecase.CancelBookingUseCase
import com.hfad.schedule.domain.usecase.GetBookingsFlowUseCase
import com.hfad.schedule.domain.usecase.GetScheduleUseCase
import com.hfad.schedule.ui.mvi.Schedule.ScheduleIntents
import com.hfad.schedule.ui.mvi.Schedule.ScheduleReducer
import com.hfad.schedule.ui.mvi.Schedule.ScheduleUiState
import com.hfad.schedule.ui.model.Booking
import com.hfad.schedule.ui.model.ScheduleItem
import com.hfad.schedule.ui.model.SportFilterItem
import com.hfad.schedule.ui.mvi.Schedule.toLocalDate
import com.hfad.schedule.ui.mvi.ScheduleCard.ScheduleCardIntent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class ScheduleViewModel @Inject constructor(
    private val getScheduleUseCase: GetScheduleUseCase,
    private val bookClassUseCase: BookClassUseCase,
    private val cancelBookingUseCase: CancelBookingUseCase,
    private val getBookingsFlowUseCase: GetBookingsFlowUseCase
) : ViewModel() {

    private val reducer = ScheduleReducer()

    private val _uiState = MutableStateFlow(ScheduleUiState())
    val uiState: StateFlow<ScheduleUiState> = _uiState.asStateFlow()

    private val _sportFilters = MutableStateFlow<List<SportFilterItem>>(emptyList())
    val sportFilters: StateFlow<List<SportFilterItem>> = _sportFilters.asStateFlow()

    val bookings: StateFlow<List<Booking>> = getBookingsFlowUseCase()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    private var originalSchedule: List<ScheduleItem> = emptyList()

    @RequiresApi(Build.VERSION_CODES.O)
    fun onIntent(intent: ScheduleIntents) {
        viewModelScope.launch {
            when (intent) {
                is ScheduleIntents.LoadScheduleForDate -> loadScheduleForDate(intent.date)
                is ScheduleIntents.OnSignUpClick -> signUpForClass(intent.scheduleId)
                is ScheduleIntents.OnCancelClick -> cancelClassBooking(intent.scheduleId)
                is ScheduleIntents.OnSportFilterSelected -> applySportFilter(intent.sport)
                is ScheduleIntents.OnDateSelected -> selectDate(intent.date)
                is ScheduleIntents.OnScheduleCardIntent -> {
                    val cardId = intent.cardId
                    val cardIntent = intent.intent
                    handleCardIntent(cardId, cardIntent)
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private suspend fun loadScheduleForDate(date: LocalDate) {
        Log.d("ScheduleViewModel", "Loading schedule for $date...")
        _uiState.value = reducer.reduceToLoading(_uiState.value)

        val result = getScheduleUseCase()

        if (result.isSuccess) {
            val items = result.getOrThrow()
            originalSchedule = items

            val loadedState = reducer.reduceScheduleLoaded(
                previousState = _uiState.value,
                items = items,
                date = date
            )

            val filteredState = reducer.reduceSportFilter(
                previousState = loadedState,
                schedule = originalSchedule,
                selectedSport = loadedState.selectedSport,
                selectedDate = loadedState.selectedDate
            )

            _uiState.value = filteredState

            updateSportFilters(items)

        } else {
            Log.e("ScheduleViewModel", "Schedule load failed: ${result.exceptionOrNull()?.message}")
            _uiState.value = reducer.reduceError("Ошибка загрузки расписания")
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private suspend fun signUpForClass(scheduleId: String) {
        Log.d("ScheduleViewModel", "Signing up for class: $scheduleId")
        val result = bookClassUseCase(scheduleId)
        if (result.isSuccess) {
            Log.d("ScheduleViewModel", "Successfully signed up for $scheduleId")
            onIntent(ScheduleIntents.LoadScheduleForDate(_uiState.value.selectedDate))
        } else {
            Log.e("ScheduleViewModel", "Sign up failed: ${result.exceptionOrNull()?.message}")
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private suspend fun cancelClassBooking(scheduleId: String) {
        Log.d("ScheduleViewModel", "Cancelling booking for: $scheduleId")
        val result = cancelBookingUseCase(scheduleId)
        if (result.isSuccess) {
            Log.d("ScheduleViewModel", "Successfully cancelled booking $scheduleId")
            onIntent(ScheduleIntents.LoadScheduleForDate(_uiState.value.selectedDate))
        } else {
            Log.e("ScheduleViewModel", "Cancel failed: ${result.exceptionOrNull()?.message}")
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun applySportFilter(sport: String?) {
        Log.d("ScheduleViewModel", "🟨 applySportFilter called with: $sport")
        _uiState.update {
            reducer.reduceSportFilter(
                previousState = it,
                schedule = originalSchedule,
                selectedSport = sport,
                selectedDate = it.selectedDate
            )
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun selectDate(date: LocalDate) {
        _uiState.update {
            reducer.reduceDateSelected(
                previousState = it,
                schedule = originalSchedule,
                selectedDate = date
            )
        }
        updateSportFilters(originalSchedule)
    }

    private fun handleCardIntent(cardId: String, intent: ScheduleCardIntent) {
        _uiState.update {
            reducer.reduceScheduleCardIntent(it, cardId, intent)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun updateSportFilters(schedule: List<ScheduleItem>) {
        val currentDate = _uiState.value.selectedDate

        val scheduleForSelectedDate = schedule.filter {
            it.timestamp.toLocalDate() == currentDate
        }

        val filters = scheduleForSelectedDate.groupingBy { it.title }
            .eachCount()
            .map { (title, count) -> SportFilterItem(title, count) }
            .sortedBy { it.title }

        _sportFilters.value = filters
    }
}
