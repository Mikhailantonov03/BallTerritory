package com.hfad.profile.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hfad.profile.domain.usecase.GetBookingHistoryUseCase
import com.hfad.profile.ui.mvi.history.HistoryIntent
import com.hfad.profile.ui.mvi.history.HistoryState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val getBookingHistoryUseCase: GetBookingHistoryUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(HistoryState())
    val state: StateFlow<HistoryState> = _state.asStateFlow()

    fun onIntent(intent: HistoryIntent) {
        when (intent) {
            is HistoryIntent.LoadHistory -> loadHistory()
        }
    }

    private fun loadHistory() {
        viewModelScope.launch {
            getBookingHistoryUseCase()
                .collect { historyItems ->
                    reduce { copy(history = historyItems) }
                }
        }
    }

    private fun reduce(reducer: HistoryState.() -> HistoryState) {
        _state.update { it.reducer() }
    }
}
