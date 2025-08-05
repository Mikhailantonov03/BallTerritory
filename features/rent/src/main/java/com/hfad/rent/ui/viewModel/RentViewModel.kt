package com.hfad.rent.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hfad.data.token.tokens.SessionManager
import com.hfad.data.token.user.UserStorage
import com.hfad.rent.domain.RentalRequest
import com.hfad.rent.domain.repository.RentRepository
import com.hfad.rent.domain.usecase.SubmitRentalRequestUseCase
import com.hfad.rent.ui.mvi.RentIntent
import com.hfad.rent.ui.mvi.RentReducer
import com.hfad.rent.ui.mvi.RentUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RentViewModel @Inject constructor(
    private val rentRepository: RentRepository,
    private val userStorage: UserStorage,
    private val sessionManager: SessionManager
) : ViewModel() {

    private val reducer = RentReducer()
    private val _uiState = MutableStateFlow(RentUiState())
    val uiState: StateFlow<RentUiState> = _uiState.asStateFlow()

    fun onIntent(intent: RentIntent) {
        when (intent) {
            is RentIntent.SelectHallType -> {
                _uiState.update {
                    reducer.reduceSelectHall(it, intent.hallType)
                }
            }

            RentIntent.SubmitRequest -> {
                viewModelScope.launch {
                    val isAuthorized = sessionManager.isLoggedIn() && !sessionManager.isGuest()

                    if (!isAuthorized) {
                        _uiState.update { reducer.reduceNeedsAuth(it) }
                        return@launch
                    }

                    val selectedHall = _uiState.value.selectedHallType
                    val user = userStorage.getUser()

                    if (selectedHall != null && user != null) {
                        val request = RentalRequest(
                            hallName = selectedHall,
                            userId = user.id,
                            userName = user.name,
                            userPhone = user.phone,
                            userEmail = user.email
                        )
                        val result = rentRepository.submitRentalRequest(request)
                        _uiState.update { reducer.reduceSubmitResult(it, result) }
                    } else {
                        _uiState.update { it.copy(errorMessage = "Не удалось получить данные пользователя или зал") }
                    }
                }
            }

            RentIntent.ResetState -> {
                _uiState.update {
                    reducer.reduceResetState(it)
                }
            }
        }
    }
}
