package com.hfad.auth.domain.repository

import com.hfad.auth.data.model.CodeRequest
import com.hfad.auth.data.model.PhoneRequest
import com.hfad.module.AuthTokens
import com.hfad.module.User

interface AuthRepository {
    suspend fun sendPhone(phone: PhoneRequest): Result<Unit>
    suspend fun verifyCode(code: CodeRequest): Result<Pair<User, AuthTokens>>
    suspend fun completeProfile(userId: String, name: String): Result<Unit>
}