package com.hfad.schedule.ui.viewModel

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hfad.module.UiStatus
import com.hfad.schedule.domain.usecase.BookClassUseCase
import com.hfad.schedule.domain.usecase.CancelBookingUseCase
import com.hfad.schedule.domain.usecase.FilterScheduleUseCase
import com.hfad.schedule.domain.usecase.GetBookingsFlowUseCase
import com.hfad.schedule.domain.usecase.GetScheduleUseCase
import com.hfad.schedule.ui.mvi.ScheduleIntents
import com.hfad.schedule.ui.mvi.ScheduleReducer
import com.hfad.schedule.ui.mvi.ScheduleUiState
import com.hfad.schedule.ui.model.Booking
import com.hfad.schedule.ui.model.ScheduleItem
import com.hfad.schedule.ui.model.SportFilterItem
import com.hfad.schedule.ui.mvi.toLocalDate
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
    private val filterScheduleUseCase: FilterScheduleUseCase,
    private val getBookingsFlowUseCase: GetBookingsFlowUseCase
) : ViewModel() {

    private val reducer = ScheduleReducer()

    private val _uiState = MutableStateFlow(ScheduleUiState())
    val uiState: StateFlow<ScheduleUiState> = _uiState.asStateFlow()

    private val _sportFilters = MutableStateFlow<List<SportFilterItem>>(emptyList())
    val sportFilters: StateFlow<List<SportFilterItem>> = _sportFilters.asStateFlow()

    val bookings: StateFlow<List<Booking>> = getBookingsFlowUseCase()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    @RequiresApi(Build.VERSION_CODES.O)
    fun onIntent(intent: ScheduleIntents) {
        viewModelScope.launch {
            when (intent) {
                is ScheduleIntents.LoadScheduleForDate -> loadScheduleForDate(intent.date)
                is ScheduleIntents.OnSignUpClick -> signUpForClass(intent.scheduleId)
                is ScheduleIntents.OnCancelClick -> cancelClassBooking(intent.scheduleId)
                is ScheduleIntents.OnSportFilterSelected -> applySportFilter(intent.sport)
                is ScheduleIntents.OnDateSelected -> selectDate(intent.date)
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
            _uiState.update {
                it.copy(
                    selectedDate = date,
                    schedule = items,
                    status = UiStatus.Idle
                )
            }
            updateSportFilters(items)
            applyCurrentFilters()
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

    private fun applySportFilter(sport: String?) {
        Log.d("ScheduleViewModel", "Applying sport filter: $sport")
        _uiState.update { it.copy(selectedSport = sport) }
        applyCurrentFilters()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun selectDate(date: LocalDate) {
        _uiState.update { it.copy(selectedDate = date) }
        applyCurrentFilters()
        updateSportFilters(_uiState.value.schedule)
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

    private fun applyCurrentFilters() {
        val currentState = _uiState.value
        val filtered = filterScheduleUseCase(
            schedule = currentState.schedule,
            selectedSport = currentState.selectedSport,
            selectedDate = currentState.selectedDate
        )
        _uiState.update {
            it.copy(filteredSchedule = filtered)
        }
    }

}
