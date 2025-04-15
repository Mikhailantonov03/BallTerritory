package com.hfad.auth.domain.usecase

import com.hfad.auth.domain.model.SaveResult
import com.hfad.data.token.tokens.TokenStorage
import com.hfad.data.token.user.UserStorage

import com.hfad.module.AuthTokens
import com.hfad.module.User
import javax.inject.Inject

class SaveAuthDataUseCase @Inject constructor(
    private val tokenStorage: TokenStorage,
    private val userStorage: UserStorage
) {
    suspend operator fun invoke(user: User, authTokens: AuthTokens): SaveResult {
        tokenStorage.saveTokens(
            access = authTokens.accessToken,
            refresh = authTokens.refreshToken
        )

        userStorage.saveUser(user)

        return if (authTokens.isNewUser) {
            SaveResult.NewUser(authTokens, user)
        } else {
            SaveResult.ExistingUser(authTokens.accessToken)
        }
    }
}
