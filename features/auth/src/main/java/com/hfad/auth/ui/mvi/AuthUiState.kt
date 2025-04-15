package com.hfad.auth.ui.mvi

import com.hfad.module.AuthTokens
import com.hfad.module.User


data class AuthUiState(
    val status: UiStatus = UiStatus.Idle,
    val phoneSent: Boolean = false,
    val accessToken: String? = null,
    val tokens: AuthTokens? = null,
    val user: User? = null,
    val requiresProfileCompletion: Boolean = false
)
