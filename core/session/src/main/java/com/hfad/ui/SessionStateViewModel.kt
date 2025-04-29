package com.hfad.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hfad.data.token.tokens.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SessionStateViewModel @Inject constructor(
    private val sessionManager: SessionManager
) : ViewModel() {

    private val _sessionState = MutableStateFlow(SessionState())
    val sessionState: StateFlow<SessionState> = _sessionState.asStateFlow()

    init {
        viewModelScope.launch {
            val loggedIn = sessionManager.isLoggedIn()
            val guest = sessionManager.isGuest()

            _sessionState.value = SessionState(
                isLoggedIn = loggedIn,
                isGuest = guest
            )
        }
    }
}
