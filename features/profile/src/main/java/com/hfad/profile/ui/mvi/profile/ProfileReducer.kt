package com.hfad.profile.ui.mvi.profile


class ProfileReducer {
    fun reduce(
        currentState: ProfileUiState,
        intent: ProfileIntents
    ): ProfileUiState {
        return when (intent) {
            is ProfileIntents.OnLogoutHandled -> currentState.copy(logoutEvent = false)
            else -> currentState // остальные случаи обрабатываются в VM
        }
    }
}
