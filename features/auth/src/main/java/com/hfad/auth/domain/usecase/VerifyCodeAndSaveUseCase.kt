package com.hfad.auth.domain.usecase

import com.hfad.auth.data.model.CodeRequest
import com.hfad.auth.domain.model.SaveResult
import javax.inject.Inject

class VerifyCodeAndSaveUseCase @Inject constructor(
    private val verifyCodeUseCase: VerifyCodeUseCase,
    private val saveAuthTokensUseCase: SaveAuthDataUseCase
) {
    suspend operator fun invoke(phone: String, code: String): Result<SaveResult> {
        val result = verifyCodeUseCase(CodeRequest(phone, code))

        return result.mapCatching { (user, authTokens) ->
            saveAuthTokensUseCase(user, authTokens)
        }
    }
}

