package com.hfad.profile.domain.usecase

import com.hfad.data.token.tokens.SessionManager
import com.hfad.data.token.user.UserStorage
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val userStorage: UserStorage,
    private val sessionManager: SessionManager
) {
    suspend operator fun invoke() {
        userStorage.clearUser()
        sessionManager.logout()
        sessionManager.setGuestMode(true)
    }
}
