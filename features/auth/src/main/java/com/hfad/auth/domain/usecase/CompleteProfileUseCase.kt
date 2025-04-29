package com.hfad.auth.domain.usecase

import com.hfad.auth.domain.repository.AuthRepository
import com.hfad.data.token.tokens.SessionManager
import com.hfad.data.token.user.UserStorage
import javax.inject.Inject

class CompleteProfileUseCase @Inject constructor(
    private val repository: AuthRepository,
    private val userStorage: UserStorage,
    private val sessionManager: SessionManager
) {
    suspend operator fun invoke(name: String,email: String): Result<Unit> {
        val user = userStorage.getUser()
        return if (user != null) {
           val result = repository.completeProfile(user.id, name,email)
            if (result.isSuccess) {
                sessionManager.setGuestMode(false)
                val updatedUser = user.copy(name = name, email = email)
                userStorage.saveUser(updatedUser)
            }
            result
        } else {
            Result.failure(IllegalStateException("User not found in storage"))
        }
    }
}