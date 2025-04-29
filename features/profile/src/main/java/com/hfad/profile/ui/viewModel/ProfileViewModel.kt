package com.hfad.profile.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hfad.data.token.user.UserStorage
import com.hfad.profile.domain.usecase.LogoutUseCase

import com.hfad.profile.ui.mvi.profile.ProfileIntents
import com.hfad.profile.ui.mvi.profile.ProfileReducer
import com.hfad.profile.ui.mvi.profile.ProfileUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userStorage: UserStorage,
    private val logoutUseCase: LogoutUseCase
) : ViewModel() {

    private val reducer = ProfileReducer()
    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    fun onIntent(intent: ProfileIntents) {
        viewModelScope.launch {
            when (intent) {
                is ProfileIntents.LoadProfile -> {
                    _uiState.update { it.copy(user = userStorage.getUser()) }
                }

                is ProfileIntents.OnLogoutClicked -> {
                    logoutUseCase()
                    _uiState.update { it.copy(logoutEvent = true) }
                }

                is ProfileIntents.OnLogoutHandled -> {
                    _uiState.update { reducer.reduce(it, intent) }
                }

                is ProfileIntents.OnEditRequested -> {
                    _uiState.update { it.copy(isEditing = true) }
                }

                is ProfileIntents.OnEditSubmitted -> {
                    val currentUser = userStorage.getUser()
                    if (currentUser != null) {
                        val updated = currentUser.copy(name = intent.name, email = intent.email)
                        userStorage.saveUser(updated)
                        _uiState.update {
                            it.copy(user = updated, isEditing = false)
                        }
                    }
                }

                is ProfileIntents.OnEditDismissed -> {
                    _uiState.update { it.copy(isEditing = false) }
                }
            }
        }
    }
}

