package com.hfad.data.token.tokens

import javax.inject.Inject

class SessionManager @Inject constructor(
    private val tokenStorage: TokenStorage
) {
    suspend fun isLoggedIn(): Boolean {
        return tokenStorage.getAccessToken()?.isNotEmpty() == true
    }
}
