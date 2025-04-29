package com.hfad.ui

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
    class LoginEventViewModel @Inject constructor() : ViewModel() {

    private val _shouldNavigateToLogin = MutableStateFlow(false)
    val shouldNavigateToLogin: StateFlow<Boolean> = _shouldNavigateToLogin

    fun requestLogin() {
        _shouldNavigateToLogin.value = true
    }

    fun reset() {
        _shouldNavigateToLogin.value = false
    }
}
