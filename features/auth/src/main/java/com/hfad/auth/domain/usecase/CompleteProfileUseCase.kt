package com.hfad.auth.domain.usecase

import com.hfad.auth.domain.repository.AuthRepository
import com.hfad.data.token.user.UserStorage
import javax.inject.Inject

class CompleteProfileUseCase @Inject constructor(
    private val repository: AuthRepository,
    private val userStorage: UserStorage
) {
    suspend operator fun invoke(name: String): Result<Unit> {
        val user = userStorage.getUser()
        return if (user != null) {
            repository.completeProfile(user.id, name)
        } else {
            Result.failure(IllegalStateException("User not found in storage"))
        }
    }
}