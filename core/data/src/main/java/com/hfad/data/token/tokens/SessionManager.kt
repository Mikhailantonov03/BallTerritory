package com.hfad.data.token.tokens

import javax.inject.Inject

class SessionManager @Inject constructor(
    private val tokenStorage: TokenStorage
) {
    suspend fun isLoggedIn(): Boolean {
        return tokenStorage.getAccessToken()?.isNotEmpty() == true
    }

    suspend fun isGuest(): Boolean {
        return tokenStorage.isGuestMode()
    }

    suspend fun setGuestMode(enabled: Boolean) {
        tokenStorage.setGuestMode(enabled)
    }

    suspend fun logout() {
        tokenStorage.clearTokens()
        tokenStorage.setGuestMode(false)
    }
}
