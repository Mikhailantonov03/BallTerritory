package com.hfad.coaches.ui.mvi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hfad.coaches.domain.usecase.GetCoachesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CoachesViewModel @Inject constructor(
    private val getCoachesUseCase: GetCoachesUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<CoachesState>(CoachesState.Loading)
    val state: StateFlow<CoachesState> = _state.asStateFlow()

    fun onIntent(intent: CoachesIntent) {
        when (intent) {
            is CoachesIntent.LoadCoaches -> loadCoaches()
            is CoachesIntent.OnCoachClick -> {
                // Здесь можно позже добавить SingleEvent для навигации
                // _event.emit(CoachesEvent.NavigateToDetails(intent.coachId))
            }
        }
    }

    private fun loadCoaches() {
        viewModelScope.launch {
            try {
                val coaches = getCoachesUseCase()
                _state.value = CoachesState.Success(coaches)
            } catch (e: Exception) {
                _state.value = CoachesState.Error(e.message ?: "Ошибка загрузки")
            }
        }
    }
}
